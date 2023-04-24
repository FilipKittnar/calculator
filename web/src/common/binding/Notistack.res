module SnackbarProvider = {
  @react.component @module("notistack")
  external make: (~children: Jsx.element) => React.element = "SnackbarProvider"
}

type snackbarOptions = {key?: string}

type snackbar

@module("notistack")
external useSnackbar: unit => snackbar = "useSnackbar"

@send
external enqueueSnackbar: (snackbar, Jsx.element, ~options: snackbarOptions=?) => string =
  "enqueueSnackbar"
