import { Frame } from '@stomp/stompjs';
import { WsResponse } from './Websocket.types';

function validateMessage(message: Frame) {
  if (!message) {
    throw new Error('no message in response');
  }
  if (!message.body) {
    throw new Error('no body in message');
  }
}

function validatePayload(wsResponse: WsResponse) {
  const { type, payload } = wsResponse;
  if (!type) {
    throw new Error('no type in response');
  }
  if (!payload) {
    throw new Error('no payload in response');
  }
}

export function basicValidation(message: Frame): WsResponse {
  validateMessage(message);
  const response: WsResponse = JSON.parse(message.body);
  const trackingId = response.trackingId || message.headers?.trackingId;
  validatePayload(response);

  return {
    ...response,
    trackingId,
  };
}
