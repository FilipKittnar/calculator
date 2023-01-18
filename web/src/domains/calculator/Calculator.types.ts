import { APP_NAMESPACE } from 'app/App.constants';
import { NO_DATA_STATE } from 'utils/dataStatus';

export const ADDITION = 'ADDITION';
export const SUBTRACTION = 'SUBTRACTION';
export const MULTIPLICATION = 'MULTIPLICATION';
export const DIVISION = 'DIVISION';
export type Operator = typeof ADDITION | typeof SUBTRACTION | typeof MULTIPLICATION | typeof DIVISION;

export const FETCH_CALCULATOR_URL = '/calculator/get';
export const PERFORM_CALCULATE_URL = '/calculator/perform-calculate';
export const CLEAR_MEMORY_URL = '/calculator/memory/clear';
export const CLEAR_HISTORY_URL = '/calculator/history/clear';
export const REMOVE_HISTORY_ROW_URL = '/calculator/history/remove';
export const CLEAR_ENTRY_URL = '/calculator/entry/clear';
export const ADD_NUMBER_TO_ENTRY_URL = '/calculator/entry/add/number';
export const ADD_DECIMAL_SEPARATOR_TO_ENTRY_URL = '/calculator/entry/add/decimal-separator';
export const REMOVE_LAST_CHARACTER_FROM_ENTRY_URL = '/calculator/entry/remove-last-character';
export const SELECT_OPERATOR_URL = '/calculator/operator/select';

export const CALCULATOR_CONTEXT_NAMESPACE = `${APP_NAMESPACE}/CALCULATOR`;
export const FETCH_CALCULATOR = `${CALCULATOR_CONTEXT_NAMESPACE}/FETCH_CALCULATOR`;
export const FETCH_CALCULATOR_RESPONSE = `${CALCULATOR_CONTEXT_NAMESPACE}/GET_CALCULATOR_RESPONSE`;
export const PERFORM_CALCULATION = `${CALCULATOR_CONTEXT_NAMESPACE}/PERFORM_CALCULATION`;
export const FETCH_CALCULATE_ERROR_RESPONSE = `${CALCULATOR_CONTEXT_NAMESPACE}/GET_CALCULATE_ERROR_RESPONSE`;
export const CLEAR_MEMORY = `${CALCULATOR_CONTEXT_NAMESPACE}/CLEAR_MEMORY`;
export const CLEAR_HISTORY = `${CALCULATOR_CONTEXT_NAMESPACE}/CLEAR_HISTORY`;
export const REMOVE_HISTORY_ROW = `${CALCULATOR_CONTEXT_NAMESPACE}/REMOVE_HISTORY_ROW`;
export const CLEAR_ENTRY = `${CALCULATOR_CONTEXT_NAMESPACE}/CLEAR_ENTRY`;
export const ADD_NUMBER_TO_ENTRY = `${CALCULATOR_CONTEXT_NAMESPACE}/ADD_NUMBER_TO_ENTRY`;
export const ADD_DECIMAL_SEPARATOR_TO_ENTRY = `${CALCULATOR_CONTEXT_NAMESPACE}/ADD_DECIMAL_SEPARATOR_TO_ENTRY`;
export const REMOVE_LAST_CHARACTER_FROM_ENTRY = `${CALCULATOR_CONTEXT_NAMESPACE}/REMOVE_LAST_CHARACTER_FROM_ENTRY`;
export const SELECT_OPERATOR = `${CALCULATOR_CONTEXT_NAMESPACE}/SELECT_OPERATOR`;

export const ZERO_DIVISION_ATTEMPTED = 'ZERO_DIVISION_ATTEMPTED';
export const MISSING_OPERATION = 'MISSING_OPERATION';
export const MISSING_ENTRY = 'MISSING_ENTRY';

export type CalculationErrorType = typeof ZERO_DIVISION_ATTEMPTED | typeof MISSING_OPERATION | typeof MISSING_ENTRY;

export interface CalculationErrorResponse {
  errorType: CalculationErrorType;
}

export interface FetchCalculatorResponse {
  total: number;
  entry?: string;
  currentOperation?: Operator;
  results?: HistoryRow[];
}

export interface AddNumberToEntryRequest {
  number: number;
}

export interface SelectOperationTypeRequest {
  operationType: Operator;
}

export interface RemoveHistoryRowRequest {
  rowId: string;
}

export interface HistoryRow {
  rowId: string;
  value: number;
  operation: Operator;
  incomingValue: number;
  result: number;
}

export interface Calculator {
  value?: number;
  entry?: string;
  latestResult?: number;
  currentOperation?: Operator;
  results: HistoryRow[];
}

export interface CalculatorState {
  calculator: Calculator | NO_DATA_STATE;
}
