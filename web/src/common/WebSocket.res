module Types = WebSocket_Types

let validateMessage = (message: option<Stomp.frame>) => {
  if message->Belt.Option.isNone {
    Js.Exn.raiseError("no message in response")
  }
  if message->Belt.Option.flatMap(({?body}) => body)->Belt.Option.isNone {
    Js.Exn.raiseError("no body in message")
  }

  message->Belt.Option.getUnsafe
}

let validatePayload = ({?\"type", ?payload, _}: Types.response) => {
  if \"type"->Belt.Option.isNone {
    Js.Exn.raiseError("no type in response")
  }
  if payload->Belt.Option.isNone {
    Js.Exn.raiseError("no payload in response")
  }
}

let basicValidation = (message: option<Stomp.frame>): Types.response => {
  let message = message->validateMessage

  // This should be Types.response
  let response = message.body->Belt.Option.map(S.parseJsonWith(_, Calculator_Types.tStruct))

  // TODO: log
  Js.Console.log2("FKR: Got WS response: response=", response)

  {
    \"type": #basic,
    trackingId: "blabla",
    payload: response,
  }
}

let sendMessage = (stompClient, action: Types.action) => {
  let {trackingId, payload, url, _} = action
  let request: Types.request = {trackingId, payload}

  try {
    stompClient->Stomp.publish({
      destination: `${Constants.WebSocket.destinationPrefix}${url}`,
      body: ?request->Js.Json.stringifyAny,
    })
  } catch {
  | Js.Exn.Error(e) => Js.Console.error2("Error observed during stomp client publish.", e)
  }
}

let send = (~url, ~state: Storage.state, ~dispatch, payload) => {
  let action: Types.action = {
    \"type": Constants.WebSocket.sendRequest,
    url,
    payload,
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

    let {payload, url, _} = request

    payload->send(~url, ~state, ~dispatch)
  })
}

let onMessageReceived = message => {
  let _ = message->basicValidation
}

let onConnect = (~onClientConnected, ~dispatch, frame, stompClient) => {
  Js.Console.log2("WS connection success: ", frame)

  dispatch(Storage.SetWsConnected(true))
  dispatch(Storage.SetWsClient(stompClient))

  stompClient->Stomp.subscribe(
    ~destination=`/user/${Constants.WebSocket.destinationQueueMain}`,
    ~callback=onMessageReceived,
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
