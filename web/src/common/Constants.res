let appNamespace = "AXON_TRAINING_APP"

module WebSocket = {
  let namespace = `${appNamespace}/WEBSOCKET`

  let sendRequest = `${namespace}/SEND_REQUEST`

  let destinationPrefix = "/app"
  let destinationQueueMain = "queue/main"
}

module Calculator = {
  let fetchCalculatorUrl = "/calculator/get"
  let performCalculateUrl = "/calculator/perform-calculate"
  let clearMemoryUrl = "/calculator/memory/clear"
  let clearHistoryUrl = "/calculator/history/clear"
  let removeHistoryRowUrl = "/calculator/history/remove"
  let clearEntryUrl = "/calculator/entry/clear"
  let addNumberToEntryUrl = "/calculator/entry/add/number"
  let addDecimalSeparatorToEntryUrl = "/calculator/entry/add/decimal-separator"
  let removeLastCharacterFromEntryUrl = "/calculator/entry/remove-last-character"
  let selectOperatorUrl = "/calculator/operator/select"

  let calculatorContextNamespace = `${appNamespace}/CALCULATOR`
  let fetchCalculatorResponse = `${calculatorContextNamespace}/GET_CALCULATOR_RESPONSE`
  let fetchCalculateErrorResponse = `${calculatorContextNamespace}/GET_CALCULATE_ERROR_RESPONSE`
}
