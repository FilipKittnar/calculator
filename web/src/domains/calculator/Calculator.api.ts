import {
  ADD_DECIMAL_SEPARATOR_TO_ENTRY_URL,
  ADD_NUMBER_TO_ENTRY_URL,
  AddNumberToEntryRequest,
  CLEAR_ENTRY_URL,
  CLEAR_HISTORY_URL,
  CLEAR_MEMORY_URL,
  FETCH_CALCULATOR_URL,
  PERFORM_CALCULATE_URL,
  REMOVE_HISTORY_ROW_URL,
  REMOVE_LAST_CHARACTER_FROM_ENTRY_URL,
  RemoveHistoryRowRequest,
  SELECT_OPERATOR_URL,
  SelectOperationTypeRequest,
} from 'domains/calculator/Calculator.types';
import { WebSocketAction } from 'domains/websockets/Websocket.types';
import { wsSendAction } from 'utils/api';

export function sendFetchCalculatorRequest(): WebSocketAction {
  return wsSendAction(FETCH_CALCULATOR_URL, {});
}

export function sendCalculateRequest(): WebSocketAction {
  return wsSendAction(PERFORM_CALCULATE_URL, {});
}

export function sendClearMemoryRequest(): WebSocketAction {
  return wsSendAction(CLEAR_MEMORY_URL, {});
}

export function sendClearHistoryRequest(): WebSocketAction {
  return wsSendAction(CLEAR_HISTORY_URL, {});
}

export function sendRemoveHistoryRowRequest(rq: RemoveHistoryRowRequest): WebSocketAction {
  return wsSendAction(REMOVE_HISTORY_ROW_URL, rq);
}

export function sendClearEntryRequest(): WebSocketAction {
  return wsSendAction(CLEAR_ENTRY_URL, {});
}

export function sendAddNumberToEntryRequest(rq: AddNumberToEntryRequest): WebSocketAction {
  return wsSendAction(ADD_NUMBER_TO_ENTRY_URL, rq);
}

export function sendAddDecimalSeparatorToEntryUrl(): WebSocketAction {
  return wsSendAction(ADD_DECIMAL_SEPARATOR_TO_ENTRY_URL, {});
}

export function sendRemoveLastCharacterFromEntryUrlRequest(): WebSocketAction {
  return wsSendAction(REMOVE_LAST_CHARACTER_FROM_ENTRY_URL, {});
}

export function sendSelectOperatorRequest(rq: SelectOperationTypeRequest): WebSocketAction {
  return wsSendAction(SELECT_OPERATOR_URL, rq);
}
