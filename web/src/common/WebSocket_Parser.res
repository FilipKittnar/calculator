module Types = WebSocket_Types

let parseMessage = (~dispatch, message) => {
  let responseType = switch message {
  | Some(message) =>
    switch message->Stomp.body {
    | Some(json) => Some((json, json->S.parseJsonWith(_, Types.responseOnlyTypeStruct)))
    | None => {
        dispatch(Storage.SetSnackbarOpen("No body in WS reponse"))
        None
      }
    }
  | None => {
      dispatch(Storage.SetSnackbarOpen("No response from WS"))
      None
    }
  }

  responseType->Belt.Option.map(((json, responseType)) =>
    responseType->Belt.Result.flatMap(({\"type"}) => {
      \"type"
      ->Belt.Result.map(() => json->S.parseJsonWith(_, Types.responseOkStruct))
      ->Belt.Result.getWithDefault(json->S.parseJsonWith(_, Types.responseErrorStruct))
    })
  )
}

let getTrackingIdFromHeaders = message =>
  message->Belt.Option.flatMap(Stomp.headers)->Belt.Option.map(headers => headers["trackingId"])

let parse = (~dispatch, message) =>
  switch message->parseMessage(~dispatch) {
  | Some(Ok(response)) => {
      let trackingId =
        response.trackingId->Belt.Option.mapWithDefault(
          message->getTrackingIdFromHeaders,
          trackingId => Some(trackingId),
        )

      {
        ...response,
        ?trackingId,
      }
    }
  | Some(Error(error)) => {
      dispatch(
        Storage.SetSnackbarOpen(
          error
          ->Js.Json.stringifyAny
          ->Belt.Option.getWithDefault("Unexpected error when parsing WS response"),
        ),
      )

      {
        \"type": Error(),
      }
    }
  | None => {
      \"type": Error(),
    }
  }
