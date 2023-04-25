open WebSocket_Types
open Calculator_Types

module type Config = {
  type context
  let defaultValue: context
}

module Make = (Config: Config) => {
  let t = React.createContext(Config.defaultValue)

  module Provider = {
    let make = React.Context.provider(t)
  }
}

type state = {
  snackbarOpen: bool,
  snackbarMessage: option<string>,
  calculator: option<Belt.Result.t<calculator, calculationErrorResponse>>,
  wsClient: option<Stomp.client>,
  wsConnected: bool,
  wsRequests: Belt.Map.String.t<action>,
}

let initialState = {
  snackbarOpen: false,
  snackbarMessage: None,
  calculator: None,
  wsClient: None,
  wsConnected: false,
  wsRequests: Belt.Map.String.empty,
}

type action =
  | SetSnackbarOpen(string)
  | SetSnackbarClosed
  | SetCalculator(calculator)
  | SetCalculatorError(calculationErrorResponse)
  | SetWsClient(Stomp.client)
  | SetWsConnected(bool)
  | AddWsRequest(action)
  | RemoveWsRequest(string)

let reducer = (state, action) =>
  switch action {
  | SetSnackbarOpen(message) => {
      ...state,
      snackbarOpen: true,
      snackbarMessage: Some(message),
    }
  | SetSnackbarClosed => {
      ...state,
      snackbarOpen: false,
    }
  | SetCalculator(calculator) => {
      ...state,
      calculator: Some(Ok(calculator)),
    }
  | SetCalculatorError(calculationErrorResponse) => {
      ...state,
      calculator: Some(Error(calculationErrorResponse)),
    }
  | SetWsClient(wsClient) => {
      ...state,
      wsClient: Some(wsClient),
    }
  | SetWsConnected(wsConnected) => {
      ...state,
      wsConnected,
    }
  | AddWsRequest(webSocketAction) => {
      ...state,
      wsRequests: state.wsRequests->Belt.Map.String.set(
        webSocketAction.trackingId,
        webSocketAction,
      ),
    }
  | RemoveWsRequest(trackingId) => {
      ...state,
      wsRequests: state.wsRequests->Belt.Map.String.remove(trackingId),
    }
  }

module Context = {
  include Make({
    type context = (state, action => unit)
    let defaultValue = (initialState, _ => ())
  })
}

module Provider = {
  @react.component
  let make = (~children) => {
    let (state, dispatch) = React.useReducer(reducer, initialState)

    <Context.Provider value=(state, dispatch)> children </Context.Provider>
  }
}
