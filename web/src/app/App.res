open ReactDOM
open Emotion

module Classes = {
  let container = Style.make(~paddingTop="24px", ())->styleToClass
}

@react.component
let make = () => {
  let (wsClientConnected, setWsClientConnected) = React.useState(() => false)
  let (state, dispatch) = React.useContext(Storage.Context.t)

  React.useEffect2(() => {
    WebSocket.resendAllPendingRequests(~state, ~dispatch)

    None
  }, (wsClientConnected, state.wsRequests))

  React.useEffect0(() => {
    if state.wsConnected {
      Js.Console.warn("WS connect received, but connection is already established")
    } else {
      let stompClient = WebSocket.setupStompClient(
        ~onClientConnected=() => setWsClientConnected(_ => true),
        ~dispatch,
      )
      stompClient->Stomp.activate

      Webapi.Dom.window->Webapi.Dom.Window.addEventListener("unload", _ =>
        if state.wsClient->Belt.Option.mapWithDefault(false, Stomp.connected) {
          state.wsClient->Belt.Option.forEach(Stomp.deactivate)
        }
      )
    }

    Some(() => state.wsClient->Belt.Option.forEach(Stomp.deactivate))
  })

  <Notistack.SnackbarProvider>
    <Mui.Container fixed=true maxWidth=Mui.Container.MaxWidth.xs className=Classes.container>
      <Alert />
      <Calculator />
    </Mui.Container>
  </Notistack.SnackbarProvider>
}
