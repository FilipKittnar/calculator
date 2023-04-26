open Emotion
open ReactDOM
open Mui

module Types = Calculator_Types
module Styles = Calculator_Styles
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

  <Grid container=true direction=#column>
    <Grid item=true>
      <Grid container=true direction=#column spacing=#2>
        <Grid item=true>
          <Standard
            calculator={state.calculator
            ->Belt.Option.map(calculator => calculator->Belt.Result.getWithDefault({results: []}))
            ->Belt.Option.getWithDefault({results: []})}
          />
        </Grid>
        <Grid item=true>
          <History />
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true>
      <Backdrop
        style={Style.make(~color="#fff", ~zIndex="1201", ())}
        \"open"={state.calculator->Belt.Option.isNone}>
        <CircularProgress color=#inherit />
      </Backdrop>
    </Grid>
  </Grid>
}
