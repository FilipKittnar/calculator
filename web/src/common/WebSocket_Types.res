type state = Sent | Pending

type action = {
  \"type": string,
  url: string,
  payload: Js.Json.t,
  trackingId: string,
  state: state,
  created: Js.Date.t,
}

type request = {
  trackingId: string,
  payload: Js.Json.t,
}

type response = {
  \"type"?: [#basic | #cors | #default | #error | #opaque | #opaqueredirect],
  trackingId: string,
  payload?: Js.Json.t,
}
