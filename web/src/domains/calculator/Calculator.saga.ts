import { put, takeEvery } from '@redux-saga/core/effects';
import { SagaIterator } from 'redux-saga';
import {
  sendAddDecimalSeparatorToEntryUrl,
  sendAddNumberToEntryRequest,
  sendCalculateRequest,
  sendClearEntryRequest,
  sendClearHistoryRequest,
  sendClearMemoryRequest,
  sendFetchCalculatorRequest,
  sendRemoveHistoryRowRequest,
  sendRemoveLastCharacterFromEntryUrlRequest,
  sendSelectOperatorRequest,
} from 'domains/calculator/Calculator.api';
import * as actions from 'domains/calculator/Calculator.store';
import { setCalculator } from 'domains/calculator/Calculator.store';
import {
  CalculationErrorResponse,
  Calculator,
  FetchCalculatorResponse,
  MISSING_ENTRY,
  MISSING_OPERATION,
  ZERO_DIVISION_ATTEMPTED,
} from 'domains/calculator/Calculator.types';
import { setSnackbarOpen } from 'domains/notification/Notification.store';
import { WsResponse } from 'domains/websockets/Websocket.types';
import { LOADING } from 'utils/dataStatus';

function* fetchCalculatorSaga(): SagaIterator {
  yield put(setCalculator(LOADING));
  yield put(sendFetchCalculatorRequest());
}

function* fetchCalculatorResponseSaga(action: WsResponse<FetchCalculatorResponse>): SagaIterator {
  const calculator = {
    ...action.payload,
  } as Calculator;

  yield put(setCalculator(calculator));
}

function* fetchCalculateErrorResponseSaga(action: WsResponse<CalculationErrorResponse>): SagaIterator {
  if (action.payload.errorType === ZERO_DIVISION_ATTEMPTED) {
    yield put(setSnackbarOpen('Cannot divide with zero'));
  }
  if (action.payload.errorType === MISSING_OPERATION) {
    yield put(setSnackbarOpen('Operation not selected'));
  }
  if (action.payload.errorType === MISSING_ENTRY) {
    yield put(setSnackbarOpen('Please fill in value'));
  }
}

function* performCalculationSaga(): SagaIterator {
  yield put(sendCalculateRequest());
}

function* clearMemorySaga(): SagaIterator {
  yield put(sendClearMemoryRequest());
}

function* clearEntrySaga(): SagaIterator {
  yield put(sendClearEntryRequest());
}

function* clearHistorySaga(): SagaIterator {
  yield put(sendClearHistoryRequest());
}

export function* removeHistoryRowSaga({ payload }: ReturnType<typeof actions.removeHistoryRow>): SagaIterator {
  yield put(sendRemoveHistoryRowRequest(payload));
}

export function* addNumberToEntrySaga({ payload }: ReturnType<typeof actions.addNumberToEntry>): SagaIterator {
  yield put(sendAddNumberToEntryRequest(payload));
}

function* addDecimalSeparatorToEntrySaga(): SagaIterator {
  yield put(sendAddDecimalSeparatorToEntryUrl());
}

export function* selectOperatorSaga({ payload }: ReturnType<typeof actions.selectOperator>): SagaIterator {
  yield put(sendSelectOperatorRequest(payload));
}

function* removeLastCharacterFromEntrySaga(): SagaIterator {
  yield put(sendRemoveLastCharacterFromEntryUrlRequest());
}

export function* CalculatorSagas(): SagaIterator {
  yield takeEvery(actions.fetchCalculator.type, fetchCalculatorSaga);
  yield takeEvery(actions.fetchCalculatorResponse.type, fetchCalculatorResponseSaga);
  yield takeEvery(actions.performCalculation.type, performCalculationSaga);
  yield takeEvery(actions.fetchCalculateErrorResponse.type, fetchCalculateErrorResponseSaga);
  yield takeEvery(actions.clearMemory.type, clearMemorySaga);
  yield takeEvery(actions.clearEntry.type, clearEntrySaga);
  yield takeEvery(actions.clearHistory.type, clearHistorySaga);
  yield takeEvery(actions.removeHistoryRow.type, removeHistoryRowSaga);
  yield takeEvery(actions.addNumberToEntry.type, addNumberToEntrySaga);
  yield takeEvery(actions.addDecimalSeparatorToEntry.type, addDecimalSeparatorToEntrySaga);
  yield takeEvery(actions.removeLastCharacterFromEntry.type, removeLastCharacterFromEntrySaga);
  yield takeEvery(actions.selectOperator.type, selectOperatorSaga);
  yield takeEvery(actions.clearMemory.type, clearMemorySaga);
  yield takeEvery(actions.clearMemory.type, clearMemorySaga);
  yield takeEvery(actions.clearMemory.type, clearMemorySaga);
}
