open ReactDOM
open Emotion
open Calculator_Types

module Classes = {
  let container =
    Style.make(
      ~padding="20px",
      ~width="300px",
      ~background="#3a4655",
      ~border="solid 1px #4a5562",
      (),
    )->styleToClass
  let historyHeader = Style.make(~fontSize="20px", ())->styleToClass
  let rowsContainer =
    Style.make(
      ~width="300px",
      ~height="200px",
      ~overflowY="auto",
      ~border="solid 1px #4a5562",
      (),
    )->styleToClass
  let iconButton = Style.make(~color="white", ())->styleToClass
}

@react.component
let make = () => {
  let (rows, setRows) = React.useState(() => [])
  let (state, dispatch) = React.useContext(Storage.Context.t)

  // TODO: log
  Js.Console.log2("FKR: Calculator history render: rows=", rows)

  React.useEffect1(() => {
    setRows(_ =>
      state.calculator
      ->Belt.Option.map(Belt.Result.map(_, ({results, _}) => results))
      ->Belt.Option.map(Belt.Result.getWithDefault(_, []))
      ->Belt.Option.getWithDefault([])
    )

    None
  }, [state.calculator])

  let serializeRowId = (~dispatch, row: historyRow) =>
    switch {rowId: ?row.rowId}->S.serializeWith(removeHistoryRowRequestStruct) {
    | Ok(json) => Some(json)
    | Error(error) => {
        dispatch(
          Storage.SetSnackbarOpen(
            error
            ->Js.Json.stringifyAny
            ->Belt.Option.getWithDefault("Unexpected error when serializing result row ID"),
          ),
        )

        None
      }
    }

  let formatRow = (row: historyRow) => {
    open Belt.Option

    [
      (row.value->map(Belt.Float.toString)->getWithDefault("") ++ " ")->Jsx.string,
      row.operation->map(Operator.toElement)->getWithDefault(Jsx.null),
      (" " ++
      row.incomingValue->map(Belt.Float.toString)->getWithDefault("") ++
      " = " ++
      row.result->map(Belt.Float.toString)->getWithDefault(""))->Jsx.string,
    ]->Jsx.array
  }

  <div className=Classes.container>
    <div className=Classes.historyHeader> {"Calculator history"->React.string} </div>
    <Mui.Grid container=true alignItems=#center>
      <Mui.IconButton
        onClick={_ => Constants.Calculator.clearHistoryUrl->WebSocket.send(~state, ~dispatch)}
        disabled={rows->Belt.Array.length == 0}
        className=Classes.iconButton>
        <Icon.Delete />
      </Mui.IconButton>
      {"Clear history"->React.string}
    </Mui.Grid>
    <div className=Classes.rowsContainer>
      {rows
      ->Belt.Array.mapWithIndex((index, row) =>
        <Mui.Grid
          container=true
          direction=#row
          alignItems=#center
          justify=#center
          key={`result-row-${index->Belt.Int.toString}`}>
          <Mui.Grid item=true>
            <Mui.IconButton
              onClick={_ =>
                Constants.Calculator.removeHistoryRowUrl->WebSocket.send(
                  ~payload=?row->serializeRowId(~dispatch),
                  ~state,
                  ~dispatch,
                )}
              className=Classes.iconButton>
              <Icon.Delete fontSize="small" />
            </Mui.IconButton>
          </Mui.Grid>
          <Mui.Grid item=true> {row->formatRow} </Mui.Grid>
        </Mui.Grid>
      )
      ->Jsx.array}
    </div>
  </div>
}
