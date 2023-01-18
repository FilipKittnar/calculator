import { v4 as uuidV4 } from 'uuid';
import { RequestState } from 'domains/websockets/enum/RequestState';
import { WebSocketAction, WS_SEND_REQUEST } from 'domains/websockets/Websocket.types';

export function wsSendAction(
  url: string,
  // eslint-disable-next-line @typescript-eslint/explicit-module-boundary-types
  payload: any,
): WebSocketAction {
  return {
    type: WS_SEND_REQUEST,
    url,
    payload,
    trackingId: uuidV4(),
    state: RequestState.SENT,
    created: new Date(),
  };
}
