let appNamespace = "AXON_TRAINING_APP"

module WebSocket = {
  let namespace = `${appNamespace}/WEBSOCKET`

  let connect = `${namespace}/CONNECT`
  let disconnect = `${namespace}/DISCONNECT`
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
  let fetchCalculator = `${calculatorContextNamespace}/FETCH_CALCULATOR`
  let fetchCalculatorResponse = `${calculatorContextNamespace}/GET_CALCULATOR_RESPONSE`
  let performCalculation = `${calculatorContextNamespace}/PERFORM_CALCULATION`
  let fetchCalculateErrorResponse = `${calculatorContextNamespace}/GET_CALCULATE_ERROR_RESPONSE`
  let clearMemory = `${calculatorContextNamespace}/CLEAR_MEMORY`
  let clearHistory = `${calculatorContextNamespace}/CLEAR_HISTORY`
  let removeHistoryRow = `${calculatorContextNamespace}/REMOVE_HISTORY_ROW`
  let clearEntry = `${calculatorContextNamespace}/CLEAR_ENTRY`
  let addNumberToEntry = `${calculatorContextNamespace}/ADD_NUMBER_TO_ENTRY`
  let addDecimalSeparatorToEntry = `${calculatorContextNamespace}/ADD_DECIMAL_SEPARATOR_TO_ENTRY`
  let removeLastCharacterFromEntry = `${calculatorContextNamespace}/REMOVE_LAST_CHARACTER_FROM_ENTRY`
  let selectOperator = `${calculatorContextNamespace}/SELECT_OPERATOR`
}
