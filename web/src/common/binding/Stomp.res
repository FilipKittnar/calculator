type frame = {body?: string}
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

@module("@stomp/stompjs") @val external frame: Js.nullable<frame> = "Frame"

@module("@stomp/stompjs") @new external createClient: clientConfig => client = "Client"

@module("@stomp/stompjs") @new external createVersions: array<string> => versions = "Versions"

@set external onConnect: (client, frame => unit) => unit = "onConnect"
@set external onStompError: (client, frame => unit) => unit = "onStompError"
@set external onWebSocketError: (client, frame => unit) => unit = "onWebSocketError"
@set external onDisconnect: (client, frame => unit) => unit = "onDisconnect"
