module Backspace = {
  @react.component @module("@material-ui/icons/Backspace")
  external make: (~color: string=?, ~className: string=?, ~fontSize: string=?) => React.element =
    "default"
}

@react.component
let make = () => {
  <Backspace />
}
