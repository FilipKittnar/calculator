open Calculator_Types

type state = Sent | Pending

type action = {
  \"type": string,
  url: string,
  payload?: Js.Json.t,
  trackingId: string,
  state: state,
  created: Js.Date.t,
}

type request = {
  trackingId: string,
  payload?: Js.Json.t,
}

type responseType = Belt.Result.t<unit, unit>
let responseTypeStruct = S.union([
  S.literalVariant(String(Constants.Calculator.fetchCalculatorResponse), Ok()),
  S.literalVariant(String(Constants.Calculator.fetchCalculateErrorResponse), Error()),
])

type responseOnlyType = {\"type": responseType}
let responseOnlyTypeStruct = S.object(o => {
  \"type": o->S.field("type", S.null(responseTypeStruct))->Belt.Option.getWithDefault(Error()),
})

type response = {
  \"type": responseType,
  trackingId?: string,
  payload?: Belt.Result.t<calculator, calculationErrorResponse>,
}
let responseOkStruct = S.object((o): response => {
  \"type": o->S.field("type", responseTypeStruct),
  trackingId: ?o->S.field("trackingId", S.null(S.string())),
  payload: ?(
    o->S.field("payload", S.null(calculatorStruct))->Belt.Option.map(payload => Ok(payload))
  ),
})
let responseErrorStruct = S.object((o): response => {
  \"type": o->S.field("type", responseTypeStruct),
  trackingId: ?o->S.field("trackingId", S.null(S.string())),
  payload: ?(
    o
    ->S.field("payload", S.null(Calculator_Types.calculationErrorResponseStruct))
    ->Belt.Option.map(payload => Error(payload))
  ),
})
