open ReactDOM
open Emotion

module Classes = {
  let container = Style.make(~height="100%", ~width="100%", ())->styleToClass
}

@react.component
let make = () => {
  <Notistack.SnackbarProvider>
    <Mui.Grid container=true className=Classes.container>
      <Snackbar />
      <Mui.Grid>
        <Calculator />
      </Mui.Grid>
    </Mui.Grid>
  </Notistack.SnackbarProvider>
}
