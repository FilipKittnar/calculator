module Operator = {
  type t = Addition | Subtraction | Multiplication | Division

  let toElement = operator =>
    switch operator {
    | Addition => <> "+" </>
    | Subtraction => <> "-" </>
    | Multiplication => <> "x" </>
    | Division => <> "÷" </>
    }
}

type historyRow = {
  rowId: string,
  value: int,
  operation: Operator.t,
  incomingValue: int,
  result: int,
}

type calculationErrorType = ZeroDivisionAttempted | MissingOperation | MissingEntry

type calculationErrorResponse = {errorType: calculationErrorType}

type t = {
  value?: int,
  entry?: string,
  latestResult?: int,
  currentOperation?: Operator.t,
  results: array<historyRow>,
}
