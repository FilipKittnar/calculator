type frame
type versions
type clientConfig = {
  brokerURL?: string,
  stompVersions?: versions,
  reconnectDelay?: int,
  heartbeatIncoming?: int,
  heartbeatOutgoing?: int,
  splitLargeFrames?: bool,
}
type client
type publishParams = {
  destination: string,
  body?: string,
}

@module("@stomp/stompjs") @val external frame: Js.nullable<frame> = "Frame"

@get external body: frame => option<string> = "body"
@get external headers: frame => option<{..}> = "headers"

@module("@stomp/stompjs") @new external createClient: clientConfig => client = "Client"

@module("@stomp/stompjs") @new external createVersions: array<string> => versions = "Versions"

@get external connected: client => bool = "connected"
@set external onConnect: (client, frame => unit) => unit = "onConnect"
@set external onStompError: (client, frame => unit) => unit = "onStompError"
@set external onWebSocketError: (client, frame => unit) => unit = "onWebSocketError"
@set external onDisconnect: (client, frame => unit) => unit = "onDisconnect"

@send
external subscribe: (client, ~destination: string, ~callback: option<frame> => unit) => unit =
  "subscribe"

@send external activate: client => unit = "activate"

@send external deactivate: client => unit = "deactivate"

@send external publish: (client, publishParams) => unit = "publish"
