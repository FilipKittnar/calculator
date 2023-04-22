module SnackbarProvider = {
  @react.component @module("notistack")
  external make: (~children: Jsx.element) => React.element = "SnackbarProvider"
}
