open Calculator_Types
open Calculator_Styles
open Icon
open Mui

@react.component
let make = () => {
  let (rows, setRows) = React.useState(() => [])
  let (state, dispatch) = React.useContext(Storage.Context.t)

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

  <Grid container=true direction=#column alignItems=#stretch className=section>
    <Grid item=true className=subSection>
      <Grid container=true justify=#center>
        <Grid item=true>
          <Typography variant=#h4> {"Calculator history"->React.string} </Typography>
        </Grid>
      </Grid>
    </Grid>
    <Grid item=true className=subSection>
      <Grid container=true alignItems=#center>
        <Grid item=true>
          <IconButton
            onClick={_ => Constants.Calculator.clearHistoryUrl->WebSocket.send(~state, ~dispatch)}
            disabled={rows->Belt.Array.length == 0}>
            <Delete />
          </IconButton>
        </Grid>
        <Grid item=true>
          <Typography> {"Clear history"->React.string} </Typography>
        </Grid>
      </Grid>
    </Grid>
    <div>
      {rows
      ->Belt.Array.mapWithIndex((index, row) =>
        <Grid
          container=true
          direction=#row
          alignItems=#center
          justify=#center
          key={`result-row-${index->Belt.Int.toString}`}>
          <Grid item=true>
            <IconButton
              onClick={_ =>
                Constants.Calculator.removeHistoryRowUrl->WebSocket.send(
                  ~payload=?row->serializeRowId(~dispatch),
                  ~state,
                  ~dispatch,
                )}>
              <Delete fontSize="small" />
            </IconButton>
          </Grid>
          <Grid item=true> {row->formatRow} </Grid>
        </Grid>
      )
      ->Jsx.array}
    </div>
  </Grid>
}
