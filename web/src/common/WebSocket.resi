let resendAllPendingRequests: (~state: Storage.state, ~dispatch: Storage.action => unit) => unit

let setupStompClient: (
  ~onClientConnected: unit => unit,
  ~dispatch: Storage.action => unit,
) => Stomp.client

let send: (
  ~payload: Js.Json.t=?,
  ~state: Storage.state,
  ~dispatch: Storage.action => 'a,
  string,
) => 'a
