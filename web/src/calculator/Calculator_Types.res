module Operator = {
  type t = Addition | Subtraction | Multiplication | Division
  let tStruct = S.union([
    S.literalVariant(String("ADDITION"), Addition),
    S.literalVariant(String("SUBTRACTION"), Subtraction),
    S.literalVariant(String("MULTIPLICATION"), Multiplication),
    S.literalVariant(String("DIVISION"), Division),
  ])

  let toElement = operator =>
    switch operator {
    | Addition => <> "+" </>
    | Subtraction => <> "-" </>
    | Multiplication => <> "x" </>
    | Division => <> "÷" </>
    }
}

type historyRow = {
  rowId?: string,
  value?: float,
  operation?: Operator.t,
  incomingValue?: float,
  result?: float,
}
let historyRowStruct = S.object(o => {
  rowId: ?o->S.field("rowId", S.option(S.string())),
  value: ?o->S.field("value", S.option(S.float())),
  operation: ?o->S.field("operation", S.option(Operator.tStruct)),
  incomingValue: ?o->S.field("incomingValue", S.option(S.float())),
  result: ?o->S.field("result", S.option(S.float())),
})

type calculationErrorType = ZeroDivisionAttempted | MissingOperation | MissingEntry
let calculationErrorTypeStruct = S.union([
  S.literalVariant(String("ZERO_DIVISION_ATTEMPTED"), ZeroDivisionAttempted),
  S.literalVariant(String("MISSING_OPERATION"), MissingOperation),
  S.literalVariant(String("MISSING_ENTRY"), MissingEntry),
])

type calculationErrorResponse = {errorType?: calculationErrorType}
let calculationErrorResponseStruct = S.object(o => {
  errorType: ?o->S.field("errorType", S.option(calculationErrorTypeStruct)),
})

type t = {
  value?: float,
  entry?: string,
  latestResult?: float,
  currentOperation?: Operator.t,
  results: array<historyRow>,
}
let tStruct = S.object(o => {
  value: ?o->S.field("value", S.option(S.float())),
  entry: ?o->S.field("entry", S.option(S.string())),
  latestResult: ?o->S.field("latestResult", S.option(S.float())),
  currentOperation: ?o->S.field("currentOperation", S.option(Operator.tStruct)),
  results: o
  ->S.field("results", S.option(S.array(historyRowStruct)))
  ->Belt.Option.getWithDefault([]),
})
