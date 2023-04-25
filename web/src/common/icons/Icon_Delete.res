module Delete = {
  @react.component @module("@material-ui/icons/Delete")
  external make: (~color: string=?, ~className: string=?, ~fontSize: string=?) => React.element =
    "default"
}

@react.component
let make = (~fontSize=?) => {
  <Delete ?fontSize />
}
