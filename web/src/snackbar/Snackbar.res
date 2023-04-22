@react.component
let make = () => {
  let (state, dispatch) = React.useContext(Storage.Context.t)

  let handleClose = _ => dispatch(SetSnackbarClosed)

  <Mui.Snackbar
    anchorOrigin={Mui.Snackbar.AnchorOrigin.make(~vertical=#top, ~horizontal=#center, ())}
    \"open"={state.snackbarOpen}
    autoHideDuration={4000->Mui.Number.int}
    onClose={_ => handleClose}>
    <MuiLab.Alert onClose=handleClose severity=#error>
      {state.snackbarMessage->Belt.Option.getWithDefault("")->Jsx.string}
    </MuiLab.Alert>
  </Mui.Snackbar>
}
