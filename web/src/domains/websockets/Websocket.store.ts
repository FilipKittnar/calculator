import { createAction, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { createSelector } from 'reselect';
import { RootState } from 'app/AppStore';
import { RequestState } from './enum/RequestState';
import { WebSocketAction, WebSocketState, WS_CONNECT, WS_DISCONNECT, WS_NAMESPACE } from './Websocket.types';
import 'map.prototype.tojson';

// Init state
const initialState: WebSocketState = {
  connected: false,
  requests: new Map<string, WebSocketAction>(),
};

// Middleware actions
export const wsConnect = createAction(WS_CONNECT);
export const wsDisconnect = createAction(WS_DISCONNECT);

// Slice
const slice = createSlice({
  name: WS_NAMESPACE,
  initialState,
  reducers: {
    setConnected: (state, { payload }: PayloadAction<boolean>) => {
      state.connected = payload;
    },
    addRequest: (state, { payload }: PayloadAction<WebSocketAction>) => {
      state.requests.set(payload.trackingId, payload);
    },
    removeRequest: (state, { payload }: PayloadAction<string>) => {
      state.requests.delete(payload);
    },
  },
});

// Actions
export const { setConnected, addRequest, removeRequest } = slice.actions;

// Getters/Selectors
export const isConnected = createSelector(
  (state: RootState) => state.websockets.connected,
  (connected) => connected,
);

export const getRequests = createSelector(
  (state: RootState) => state.websockets.requests,
  (requests) => requests,
);

export const getRequest = createSelector(
  getRequests,
  (state: RootState, trackingId: string) => trackingId,
  (requests, trackingId) => {
    return requests.get(trackingId);
  },
);

export const getPendingRequests = createSelector(getRequests, (requests) => {
  return [...requests.values()].filter((r) => r.state === RequestState.PENDING);
});

export const getPendingRequestsSorted = createSelector(getPendingRequests, (pending) => {
  return [...pending].sort((a: WebSocketAction, b: WebSocketAction) => {
    return a.created.getTime() - b.created.getTime();
  });
});

// Export reducer
export default slice.reducer;
