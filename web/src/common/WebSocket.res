let validateMessage = (message: option<Stomp.frame>) => {
  if message->Belt.Option.isNone {
    Js.Exn.raiseError("no message in response")
  }
  if message->Belt.Option.flatMap(({?body}) => body)->Belt.Option.isNone {
    Js.Exn.raiseError("no body in message")
  }
}
