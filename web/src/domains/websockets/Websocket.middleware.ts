import { Client, Frame, StompConfig, Versions } from '@stomp/stompjs';
import { Dispatch, Middleware, MiddlewareAPI } from 'redux';
import { RootState } from 'app/AppStore';
import { wsSendAction } from 'utils/api';
import { RequestState } from './enum/RequestState';
import { basicValidation } from './Websocket.mapper';
import { addRequest, getPendingRequestsSorted, isConnected, removeRequest, setConnected } from './Websocket.store';
import {
  DESTINATION_QUEUE_MAIN,
  WebSocketAction,
  WS_CONNECT,
  WS_DESTINATION_PREFIX,
  WS_DISCONNECT,
  WS_SEND_REQUEST,
} from './Websocket.types';

const serverWsBrokerUrl = process.env.REACT_APP_WS_CONNECT_URL;

export const onMessageReceived = (message: Frame, dispatch: Dispatch): void => {
  try {
    const wsResponse = basicValidation(message);
    dispatch(removeRequest(wsResponse.trackingId));
    dispatch(wsResponse);
  } catch (e) {
    console.error(message);
  }
};

const sendMessage = (stompClient: Client | null, request: WebSocketAction) => {
  if (!stompClient) {
    throw new Error('stompClient was not initialized');
  }

  const { trackingId, payload, url } = request;

  const data = JSON.stringify({ trackingId, payload });
  try {
    stompClient.publish({
      destination: WS_DESTINATION_PREFIX + url,
      body: data,
    });
  } catch (e) {
    console.error('Error observed during stomp client publish.', e);
  }
};

const onConnect = (stompClient: Client, dispatch: Dispatch, getState: () => RootState, frame?: Frame) => {
  console.log('WS connection success', frame);
  dispatch(setConnected(true));

  // subscribe to user private messages coming from backend
  stompClient.subscribe(`/user/${DESTINATION_QUEUE_MAIN}`, (message: Frame) => {
    onMessageReceived(message, dispatch);
  });

  // resend all requests pending during socket was closed
  resendAllPendingRequests(dispatch, getState);
};

const resendAllPendingRequests = (dispatch: Dispatch, getState: () => RootState) => {
  const requests = getPendingRequestsSorted(getState());

  requests.forEach((req) => {
    dispatch(removeRequest(req.trackingId));

    const { payload, url } = req;
    dispatch(wsSendAction(url, payload));
  });
};

const onError = async (dispatch: Dispatch, frame: Frame | string) => {
  console.error('WS error', frame);
  dispatch(setConnected(false));
};

const onDisconnect = (dispatch: Dispatch, frame: Frame | string) => {
  console.warn('WS disconnected', frame);
  dispatch(setConnected(false));
};

const setupStompClient = (dispatch: Dispatch, getState: () => RootState): Client => {
  const stompConfig: StompConfig = {
    brokerURL: serverWsBrokerUrl,
    stompVersions: new Versions(['1.2', '1.1', '1.0']),
    reconnectDelay: 30000,
    heartbeatIncoming: 30000,
    heartbeatOutgoing: 30000,
    splitLargeFrames: true,
  };
  const client = new Client(stompConfig);

  client.onConnect = (frame) => onConnect(client, dispatch, getState, frame);
  client.onStompError = (frame) => onError(dispatch, frame);
  client.onWebSocketError = (frame) => onError(dispatch, frame);
  client.onDisconnect = (frame) => onDisconnect(dispatch, frame);

  return client;
};

export const socketMiddleware = (): Middleware => {
  let stompClient: Client | null = null;

  window.addEventListener('unload', function () {
    if (stompClient?.connected) {
      stompClient?.deactivate();
    }
  });

  return ({ dispatch, getState }: MiddlewareAPI) => (next: Dispatch) => async (action: WebSocketAction) => {
    switch (action.type) {
      case WS_SEND_REQUEST: {
        if (isConnected(getState())) {
          sendMessage(stompClient, action);
          dispatch(addRequest(action));
        } else {
          action.state = RequestState.PENDING;
          dispatch(addRequest(action));
        }
        break;
      }

      case WS_CONNECT: {
        if (isConnected(getState())) {
          console.warn('WS connect received, but connection is already established');
          break;
        }
        stompClient = setupStompClient(dispatch, getState);
        stompClient.activate();
        break;
      }

      case WS_DISCONNECT: {
        stompClient?.deactivate();
        break;
      }

      default:
        return next(action);
    }
  };
};
