open ReactDOM

type css = string
type classLabel = {label: string}

@module("@emotion/css") external objectToClass: {..} => css = "css"
@module("@emotion/css") external objectToLabeledClass: ({..}, classLabel) => css = "css"

@module("@emotion/css") external styleToClass: Style.t => css = "css"
@module("@emotion/css") external styleToLabeledClass: (Style.t, classLabel) => css = "css"

@module("@emotion/css") external stringToClass: string => css = "css"
@module("@emotion/css") external stringToLabeledClass: (string, classLabel) => css = "css"

@module("@emotion/css") external objectTokeyframes: {..} => css = "keyframes"

@module("@emotion/css") external cx: array<css> => css = "cx"

@module("@emotion/css") external injectGlobalString: string => unit = "injectGlobal"
@module("@emotion/css") external injectGlobalStyle: Style.t => unit = "injectGlobal"
@module("@emotion/css") external injectGlobalObject: {..} => unit = "injectGlobal"

let useCx1 = (~dependency=?, classNameArray) =>
  React.useMemo1(_ => cx(classNameArray), dependency->Belt.Option.getWithDefault(classNameArray))
let useCx0 = classNameArray => React.useMemo0(_ => cx(classNameArray))
