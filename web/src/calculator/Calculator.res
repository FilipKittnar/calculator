open Emotion
open ReactDOM
open Mui

module Types = Calculator_Types
module Standard = Calculator_Standard
module History = Calculator_History

module Classes = {
  let container =
    Style.make(~border="solid 1px #3a4655", ~boxShadow="0 8px 50px -7px black", ())->styleToClass
}

@react.component
let make = () => {
  let (state, dispatch) = React.useContext(Storage.Context.t)

  React.useEffect0(() => {
    Constants.Calculator.fetchCalculatorUrl->WebSocket.send(~state, ~dispatch)

    None
  })

  React.useEffect1(() => {
    state.calculator->Belt.Option.forEach(calculator =>
      switch calculator {
      | Error({?errorType}) =>
        dispatch(
          SetSnackbarOpen(
            switch errorType {
            | Some(ZeroDivisionAttempted) => "Cannot divide with zero"
            | Some(MissingOperation) => "Operation not selected"
            | Some(MissingEntry) => "Please fill in value"
            | None => "Unexpected error"
            },
          ),
        )
      | Ok(_) => ()
      }
    )

    None
  }, [state.calculator])

  <div className=Classes.container>
    <Grid container=true direction=#row>
      <Standard
        calculator={state.calculator
        ->Belt.Option.map(calculator => calculator->Belt.Result.getWithDefault({results: []}))
        ->Belt.Option.getWithDefault({results: []})}
      />
      <History />
    </Grid>
    <Backdrop
      style={Style.make(~color="#fff", ~zIndex="1201", ())}
      \"open"={state.calculator->Belt.Option.isNone}>
      <CircularProgress color=#inherit />
    </Backdrop>
  </div>
}
