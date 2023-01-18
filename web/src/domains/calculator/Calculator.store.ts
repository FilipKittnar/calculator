import { createAction, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { createSelector } from 'reselect';
import { RootState } from 'app/AppStore';
import { getData, NO_DATA_STATE } from 'utils/dataStatus';
import {
  ADD_DECIMAL_SEPARATOR_TO_ENTRY,
  ADD_NUMBER_TO_ENTRY,
  AddNumberToEntryRequest,
  Calculator,
  CALCULATOR_CONTEXT_NAMESPACE,
  CalculatorState,
  CLEAR_ENTRY,
  CLEAR_HISTORY,
  CLEAR_MEMORY,
  FETCH_CALCULATE_ERROR_RESPONSE,
  FETCH_CALCULATOR,
  FETCH_CALCULATOR_RESPONSE,
  PERFORM_CALCULATION,
  REMOVE_HISTORY_ROW,
  REMOVE_LAST_CHARACTER_FROM_ENTRY,
  RemoveHistoryRowRequest,
  SELECT_OPERATOR,
  SelectOperationTypeRequest,
} from './Calculator.types';

export const fetchCalculator = createAction(FETCH_CALCULATOR);
export const fetchCalculatorResponse = createAction(FETCH_CALCULATOR_RESPONSE);
export const performCalculation = createAction(PERFORM_CALCULATION);
export const fetchCalculateErrorResponse = createAction(FETCH_CALCULATE_ERROR_RESPONSE);
export const clearMemory = createAction(CLEAR_MEMORY);
export const clearEntry = createAction(CLEAR_ENTRY);
export const clearHistory = createAction(CLEAR_HISTORY);
export const removeHistoryRow = createAction<RemoveHistoryRowRequest>(REMOVE_HISTORY_ROW);
export const addNumberToEntry = createAction<AddNumberToEntryRequest>(ADD_NUMBER_TO_ENTRY);
export const addDecimalSeparatorToEntry = createAction(ADD_DECIMAL_SEPARATOR_TO_ENTRY);
export const removeLastCharacterFromEntry = createAction(REMOVE_LAST_CHARACTER_FROM_ENTRY);
export const selectOperator = createAction<SelectOperationTypeRequest>(SELECT_OPERATOR);

const initialState: CalculatorState = {
  calculator: undefined,
};

const slice = createSlice({
  name: CALCULATOR_CONTEXT_NAMESPACE,
  initialState,
  reducers: {
    setCalculator: (state, action: PayloadAction<Calculator | NO_DATA_STATE>) => {
      state.calculator = action.payload;
    },
  },
});

export const { setCalculator } = slice.actions;

export const getCalculator = createSelector(
  (state: RootState) => state.calculator.calculator,
  (calculator) => calculator,
);
export const getHistoryRows = createSelector(getCalculator, (calculator) => {
  return getData(calculator)?.results;
});

export default slice.reducer;
