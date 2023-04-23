open WebSocket_Types

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
  calculator: Result.t<Calculator_Types.t, Calculator_Types.calculationErrorResponse>,
  wsClient: option<Stomp.client>,
  wsConnected: bool,
  wsRequests: Belt.Map.String.t<action>,
}

let initialState = {
  snackbarOpen: false,
  snackbarMessage: None,
  calculator: NotStarted,
  wsClient: None,
  wsConnected: false,
  wsRequests: Belt.Map.String.empty,
}

type action =
  | SetSnackbarOpen(string)
  | SetSnackbarClosed
  | StartLoadingCalculator
  | SetCalculator(Calculator_Types.t)
  | SetCalculatorError(Calculator_Types.calculationErrorResponse)
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
  | StartLoadingCalculator => {
      ...state,
      calculator: Pending,
    }
  | SetCalculator(calculator) => {
      ...state,
      calculator: Ok(calculator),
    }
  | SetCalculatorError(calculationErrorResponse) => {
      ...state,
      calculator: Error(calculationErrorResponse),
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
