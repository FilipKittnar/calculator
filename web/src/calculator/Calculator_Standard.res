open ReactDOM
open Emotion
open Calculator_Types

module Classes = {
  let resultField =
    Style.make(
      ~padding="20px",
      ~width="100%",
      ~background="#3a4655",
      ~border="solid 1px #3a4655",
      (),
    )->styleToClass
  let resultFieldTopPart =
    Style.make(
      ~borderBottom="dotted 1px",
      ~height="40%",
      ~marginBottom="10px",
      ~width="100%",
      (),
    )->styleToClass
  let resultFieldBottomPart =
    Style.make(~height="60%", ~width="100%", ~fontWeight="bold", ~fontSize="20px", ())->styleToClass
  let functionButton = Style.make(~backgroundColor="#425062", ())->styleToClass
  let button =
    Style.make(
      ~width="100%",
      ~border="solid 1px #4a5562",
      ~borderRadius="0",
      ~fontSize="18px",
      ~color="white",
      (),
    )->styleToClass
  let buttonHover =
    Style.make(~backgroundColor="#9baab9", ())
    ->Utils.Style.styleWithSelectors(~cssSelectors=list{Hover})
    ->styleToClass
}

module ResultFieldTopPartValue = {
  @react.component
  let make = (~calculator: calculator) => {
    <Mui.Grid item=true className=Classes.resultFieldTopPart>
      <Mui.Grid container=true justify=#"flex-end">
        {calculator.value
        ->Belt.Option.map(Belt.Float.toString)
        ->Belt.Option.getWithDefault("")
        ->Jsx.string}
        {calculator.currentOperation
        ->Belt.Option.map(Operator.toElement)
        ->Belt.Option.getWithDefault(Jsx.null)}
      </Mui.Grid>
    </Mui.Grid>
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

    <Mui.Grid item=true className=Classes.resultFieldBottomPart>
      <Mui.Grid container=true justify=#"flex-end"> {getValue()->Jsx.string} </Mui.Grid>
    </Mui.Grid>
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

  <Mui.Grid container=true direction=#column>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        <Mui.Grid item=true className=Classes.resultField>
          <Mui.Grid container=true direction=#column>
            <ResultFieldTopPartValue calculator />
            <ResultFieldBottomPartValue calculator />
          </Mui.Grid>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ => Constants.Calculator.clearMemoryUrl->WebSocket.send(~state, ~dispatch)}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {"C"->React.string}
          </Mui.Button>
        </Mui.Grid>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ => Constants.Calculator.clearEntryUrl->WebSocket.send(~state, ~dispatch)}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {"CE"->React.string}
          </Mui.Button>
        </Mui.Grid>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.removeLastCharacterFromEntryUrl->WebSocket.send(
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            <Icon.Backspace />
          </Mui.Button>
        </Mui.Grid>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Division->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {Operator.Division->Operator.toElement}
          </Mui.Button>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        {[7., 8., 9.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Mui.Grid item=true key={`number-${index->Belt.Int.toString}`}>
            <Mui.Button
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}
              className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
              {number->React.float}
            </Mui.Button>
          </Mui.Grid>
        )
        ->Jsx.array}
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Multiplication->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {Operator.Multiplication->Operator.toElement}
          </Mui.Button>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        {[4., 5., 6.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Mui.Grid item=true key={`number-${index->Belt.Int.toString}`}>
            <Mui.Button
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}
              className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
              {number->React.float}
            </Mui.Button>
          </Mui.Grid>
        )
        ->Jsx.array}
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Subtraction->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {Operator.Subtraction->Operator.toElement}
          </Mui.Button>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        {[1., 2., 3.]
        ->Belt.Array.mapWithIndex((index, number) =>
          <Mui.Grid item=true key={`number-${index->Belt.Int.toString}`}>
            <Mui.Button
              onClick={_ =>
                Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                  ~payload=?number->serializeNumber(~dispatch),
                  ~state,
                  ~dispatch,
                )}
              className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
              {number->React.float}
            </Mui.Button>
          </Mui.Grid>
        )
        ->Jsx.array}
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.selectOperatorUrl->WebSocket.send(
                ~payload=?Operator.Addition->serializeOperator(~dispatch),
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {Operator.Addition->Operator.toElement}
          </Mui.Button>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
    <Mui.Grid item=true>
      <Mui.Grid container=true direction=#row>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.addNumberToEntryUrl->WebSocket.send(
                ~payload=?0.->serializeNumber(~dispatch),
                ~state,
                ~dispatch,
              )}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {0.->React.float}
          </Mui.Button>
        </Mui.Grid>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.addDecimalSeparatorToEntryUrl->WebSocket.send(~state, ~dispatch)}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {","->React.string}
          </Mui.Button>
        </Mui.Grid>
        <Mui.Grid item=true>
          <Mui.Button
            onClick={_ =>
              Constants.Calculator.performCalculateUrl->WebSocket.send(~state, ~dispatch)}
            className={[Classes.functionButton, Classes.button, Classes.buttonHover]->cx}>
            {"="->React.string}
          </Mui.Button>
        </Mui.Grid>
      </Mui.Grid>
    </Mui.Grid>
  </Mui.Grid>
}
