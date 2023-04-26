open Calculator_Types
open ReactDOM
open Emotion
open Calculator_Styles
open Icon
open Mui

module Classes = {
  let resultLine = Style.make(~minHeight="1.6rem", ())->styleToClass
  let button = (theme: Theme.t) =>
    Style.make(
      ~border="outset 1px",
      ~backgroundColor=theme.palette.grey.\"300",
      ~fontWeight="bold",
      (),
    )->styleToClass
}

module ResultFieldTopPartValue = {
  @react.component
  let make = (~calculator: calculator) => {
    <Grid item=true className={[Classes.resultLine, subSection]->cx}>
      <Grid container=true direction=#row justify=#"flex-end" spacing=#1>
        <Grid item=true>
          <Typography>
            {calculator.value
            ->Belt.Option.map(Belt.Float.toString)
            ->Belt.Option.getWithDefault("")
            ->Jsx.string}
          </Typography>
        </Grid>
        <Grid item=true>
          <Typography>
            {calculator.currentOperation
            ->Belt.Option.map(Operator.toElement)
            ->Belt.Option.getWithDefault(Jsx.null)}
          </Typography>
        </Grid>
      </Grid>
    </Grid>
  }
}

module ResultFieldBottomPartValue = {
  @react.component
  let make = (~calculator) => {
    let onlyResultPresent = () =>
      calculator.entry->Belt.Option.isNone &&
      calculator.value->Belt.Option.isNone &&
      calculator.currentOperation->Belt.Option.isNone

    let getValue = () =>
      (
        onlyResultPresent()
          ? calculator.latestResult->Belt.Option.map(Belt.Float.toString)
          : calculator.entry
      )->Belt.Option.getWithDefault("")

    <Grid item=true className=Classes.resultLine>
      <Grid container=true direction=#row justify=#"flex-end">
        <Grid item=true>
          <Typography> {getValue()->Jsx.string} </Typography>
        </Grid>
      </Grid>
    </Grid>
  }
}

module CalcButton = {
  @react.component
  let make = (~onClick, ~children) => {
    let theme = Core.useTheme()

    <Button variant=#text fullWidth=true onClick className={Classes.button(theme)}>
      children
    </Button>
  }
}

@react.component
let make = (~calculator) => {
  let (state, dispatch) = React.useContext(Storage.Context.t)

  let serializeOperator = (~dispatch, operator) =>
    switch {operationType: operator}->S.serializeWith(selectOperationTypeRequestStruct) {
    | Ok(json) => Some(json)
    | Error(error) => {
        dispatch(
          Storage.SetSnackbarOpen(
            error
            ->Js.Json.stringifyAny
            ->Belt.Option.getWithDefault("Unexpected error when serializing operator"),
          ),
        )

        None
      }
    }

  let serializeNumber = (~dispatch, number) =>
    switch {number: number}->S.serializeWith(addNumberToEntryRequestStruct) {
    | Ok(json) => Some(json)
    | Error(error) => {
        dispatch(
          Storage.SetSnackbarOpen(
            error
            ->Js.Json.stringifyAny
            ->Belt.Option.getWithDefault("Unexpected error when serializing number"),
          ),
        )

        None
      }
    }

  <Grid container=true direction=#column alignItems=#stretch>
    <Grid item=true className=section>
      <Grid container=true direction=#column>
        <ResultFieldTopPartValue calculator />
        <ResultFieldBottomPartValue calculator />
      </Grid>
    </Grid>
    <Grid item=true>
      <Grid container=true direction=#row justify=#center>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ => Constants.Calculator.clearMemoryUrl->WebSocket.send(~state, ~dispatch)}>
            {"C"->React.string}
          </CalcButton>
        </Grid>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ => Constants.Calculator.clearEntryUrl->WebSocket.send(~state, ~dispatch)}>
            {"CE"->React.string}
          </CalcButton>
        </Grid>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.removeLastCharacterFromEntryUrl->WebSocket.send(
                ~state,
                ~dispatch,
              )}>
            <Backspace />
          </CalcButton>
        </Grid>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Division->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}>
            {Operator.Division->Operator.toElement}
          </CalcButton>
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true>
      <Grid container=true direction=#row justify=#center>
        {[7., 8., 9.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Grid item=true xs=Grid.Xs.\"3" key={`number-${index->Belt.Int.toString}`}>
            <CalcButton
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}>
              {number->React.float}
            </CalcButton>
          </Grid>
        )
        ->Jsx.array}
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Multiplication->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}>
            {Operator.Multiplication->Operator.toElement}
          </CalcButton>
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true>
      <Grid container=true direction=#row justify=#center>
        {[4., 5., 6.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Grid item=true xs=Grid.Xs.\"3" key={`number-${index->Belt.Int.toString}`}>
            <CalcButton
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}>
              {number->React.float}
            </CalcButton>
          </Grid>
        )
        ->Jsx.array}
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Subtraction->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}>
            {Operator.Subtraction->Operator.toElement}
          </CalcButton>
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true>
      <Grid container=true direction=#row>
        {[1., 2., 3.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Grid item=true xs=Grid.Xs.\"3" key={`number-${index->Belt.Int.toString}`}>
            <CalcButton
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}>
              {number->React.float}
            </CalcButton>
          </Grid>
        )
        ->Jsx.array}
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Addition->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}>
            {Operator.Addition->Operator.toElement}
          </CalcButton>
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true>
      <Grid container=true direction=#row justify=#center>
        <Grid item=true xs=Grid.Xs.\"6">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                ~payload=?0.->serializeNumber(~dispatch),
                ~state,
                ~dispatch,
              )}>
            {0.->React.float}
          </CalcButton>
        </Grid>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.addDecimalSeparatorToEntryUrl->WebSocket.send(
                ~state,
                ~dispatch,
              )}>
            {","->React.string}
          </CalcButton>
        </Grid>
        <Grid item=true xs=Grid.Xs.\"3">
          <CalcButton
            onClick={_ =>
              Constants.Calculator.performCalculateUrl->WebSocket.send(~state, ~dispatch)}>
            {"="->React.string}
          </CalcButton>
        </Grid>
      </Grid>
    </Grid>
  </Grid>
}
