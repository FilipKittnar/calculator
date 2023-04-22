type t<'successfulResult, 'errorResult> =
  | NotStarted
  | Pending
  | Ok('successfulResult)
  | Error('errorResult)

let map = (operationResult, mapFunction) =>
  switch operationResult {
  | NotStarted => NotStarted
  | Pending => Pending
  | Ok(successfulResult) => Ok(mapFunction(successfulResult))
  | Error(errorResult) => Error(errorResult)
  }

let mapWithDefault = (operationResult, defaultValue, mapFunction) =>
  switch operationResult {
  | NotStarted => defaultValue
  | Pending => defaultValue
  | Ok(successfulResult) => mapFunction(successfulResult)
  | Error(_) => defaultValue
  }

let mapErrorWithDefault = (operationResult, defaultValue, mapFunction) =>
  switch operationResult {
  | NotStarted => defaultValue
  | Pending => defaultValue
  | Ok(_) => defaultValue
  | Error(errorResult) => mapFunction(errorResult)
  }

let getWithDefault = (operationResult, defaultValue) =>
  mapWithDefault(operationResult, defaultValue, value => value)

let isNotStarted = operationResult =>
  switch operationResult {
  | NotStarted => true
  | Pending | Ok(_) | Error(_) => false
  }
let isPending = operationResult =>
  switch operationResult {
  | Pending => true
  | NotStarted | Ok(_) | Error(_) => false
  }
let isSuccess = operationResult => mapWithDefault(operationResult, false, _ => true)
let isError = operationResult => mapErrorWithDefault(operationResult, false, _ => true)

let forSuccess = (operationResult, callback) =>
  switch operationResult {
  | NotStarted | Pending | Error(_) => ()
  | Ok(successfulResult) => callback(successfulResult)
  }
