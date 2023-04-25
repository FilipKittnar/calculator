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

type addNumberToEntryRequest = {number?: float}
let addNumberToEntryRequestStruct = S.object(o => {
  number: ?o->S.field("number", S.null(S.float())),
})

type selectOperationTypeRequest = {operationType?: Operator.t}
let selectOperationTypeRequestStruct = S.object(o => {
  operationType: ?o->S.field("operationType", S.null(Operator.tStruct)),
})

type removeHistoryRowRequest = {rowId?: string}
let removeHistoryRowRequestStruct = S.object(o => {
  rowId: ?o->S.field("rowId", S.null(S.string())),
})

type historyRow = {
  rowId?: string,
  value?: float,
  operation?: Operator.t,
  incomingValue?: float,
  result?: float,
}
let historyRowStruct = S.object(o => {
  rowId: ?o->S.field("rowId", S.null(S.string())),
  value: ?o->S.field("value", S.null(S.float())),
  operation: ?o->S.field("operation", S.null(Operator.tStruct)),
  incomingValue: ?o->S.field("incomingValue", S.null(S.float())),
  result: ?o->S.field("result", S.null(S.float())),
})

type calculationErrorType = ZeroDivisionAttempted | MissingOperation | MissingEntry
let calculationErrorTypeStruct = S.union([
  S.literalVariant(String("ZERO_DIVISION_ATTEMPTED"), ZeroDivisionAttempted),
  S.literalVariant(String("MISSING_OPERATION"), MissingOperation),
  S.literalVariant(String("MISSING_ENTRY"), MissingEntry),
])

type calculationErrorResponse = {errorType?: calculationErrorType}
let calculationErrorResponseStruct = S.object(o => {
  errorType: ?o->S.field("errorType", S.null(calculationErrorTypeStruct)),
})

type calculator = {
  value?: float,
  entry?: string,
  latestResult?: float,
  currentOperation?: Operator.t,
  results: array<historyRow>,
}
let calculatorStruct = S.object(o => {
  value: ?o->S.field("value", S.null(S.float())),
  entry: ?o->S.field("entry", S.null(S.string())),
  latestResult: ?o->S.field("latestResult", S.null(S.float())),
  currentOperation: ?o->S.field("currentOperation", S.null(Operator.tStruct)),
  results: o->S.field("results", S.array(historyRowStruct)),
})
