module Types = WebSocket_Types
module Parser = WebSocket_Parser

let sendMessage = (stompClient, action: Types.action) => {
  let {trackingId, ?payload, url, _} = action
  let request: Types.request = {trackingId, ?payload}

  stompClient->Stomp.publish({
    destination: `${Constants.WebSocket.destinationPrefix}${url}`,
    body: ?request->Js.Json.stringifyAny,
  })
}

let send = (~payload=?, ~state: Storage.state, ~dispatch, url) => {
  let action: Types.action = {
    \"type": Constants.WebSocket.sendRequest,
    url,
    ?payload,
    trackingId: Uuid.V4.make(),
    state: Types.Sent,
    created: Js.Date.make(),
  }

  switch (state.wsClient, state.wsConnected) {
  | (Some(stompClient), true) => {
      stompClient->sendMessage(action)
      dispatch(Storage.AddWsRequest(action))
    }
  | _ => dispatch(Storage.AddWsRequest({...action, state: Types.Pending}))
  }
}

let resendAllPendingRequests = (~state: Storage.state, ~dispatch) => {
  state.wsRequests
  ->Belt.Map.String.keep((_trackingId, action) => action.state == Types.Pending)
  ->Belt.Map.String.valuesToArray
  ->Belt.List.fromArray
  ->Belt.List.sort((action1, action2) => action1.created->ReDate.compareAsc(action2.created))
  ->Belt.List.forEach(request => {
    dispatch(Storage.RemoveWsRequest(request.trackingId))

    let {?payload, url, _} = request

    url->send(~payload?, ~state, ~dispatch)
  })
}

let onMessageReceived = (~dispatch, message) => {
  let reponse = message->Parser.parse(~dispatch)

  reponse.trackingId->Belt.Option.forEach(trackingId =>
    dispatch(Storage.RemoveWsRequest(trackingId))
  )

  switch reponse.payload {
  | Some(Ok(calculator)) => dispatch(Storage.SetCalculator(calculator))
  | Some(Error(error)) => dispatch(Storage.SetCalculatorError(error))
  | None => dispatch(Storage.SetSnackbarOpen("No payload in WS response body"))
  }
}

let onConnect = (~onClientConnected, ~dispatch, frame, stompClient) => {
  Js.Console.log2("WS connection success: ", frame)

  dispatch(Storage.SetWsConnected(true))
  dispatch(Storage.SetWsClient(stompClient))

  stompClient->Stomp.subscribe(
    ~destination=`/user/${Constants.WebSocket.destinationQueueMain}`,
    ~callback=onMessageReceived(~dispatch),
  )

  onClientConnected()
}

let onError = (~dispatch, frame) => {
  Js.Console.error2("WS error ", frame)

  dispatch(Storage.SetWsConnected(false))
}

let onDisconnect = (~dispatch, frame) => {
  Js.Console.error2("WS disconnected ", frame)

  dispatch(Storage.SetWsConnected(false))
}

let setupStompClient = (~onClientConnected, ~dispatch) => {
  let client = Stomp.createClient({
    brokerURL: "ws://localhost:8080/ws",
    stompVersions: Stomp.createVersions(["1.2", "1.1", "1.0"]),
    reconnectDelay: 30_000,
    heartbeatIncoming: 30_000,
    heartbeatOutgoing: 30_000,
    splitLargeFrames: true,
  })

  client->Stomp.onConnect(onConnect(_, client, ~onClientConnected, ~dispatch))
  client->Stomp.onStompError(onError(~dispatch))
  client->Stomp.onWebSocketError(onError(~dispatch))
  client->Stomp.onDisconnect(onDisconnect(~dispatch))

  client
}
