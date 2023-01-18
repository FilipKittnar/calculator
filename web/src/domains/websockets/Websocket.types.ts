import { APP_NAMESPACE } from 'app/App.constants';
import { RequestState } from './enum/RequestState';

export const WS_NAMESPACE = `${APP_NAMESPACE}/WEBSOCKET`;

export const WS_CONNECT = `${WS_NAMESPACE}/CONNECT`;
export const WS_DISCONNECT = `${WS_NAMESPACE}/DISCONNECT`;
export const WS_SEND_REQUEST = `${WS_NAMESPACE}/SEND_REQUEST`;

export const WS_DESTINATION_PREFIX = '/app';
export const DESTINATION_QUEUE_MAIN = 'queue/main';

export interface WebSocketState {
  // connection to the backend is established
  connected: boolean;
  // list of requests which should be processed by backend
  requests: Map<string, WebSocketAction>;
}

export interface WebSocketAction {
  type: string;
  url: string;
  payload: any;
  trackingId: string;
  state: RequestState;
  // time when request was created on the frontend side
  created: Date;
}

export interface WsRequest<T = any> {
  trackingId: string;
  payload: T;
}

export interface WsResponse<T = any> {
  type: ResponseType;
  trackingId: string;
  payload: T;
}
