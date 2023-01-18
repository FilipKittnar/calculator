import { CalculatorSagas } from 'domains/calculator/Calculator.saga';
import CalculatorReducer from 'domains/calculator/Calculator.store';
import NotificationReducer from 'domains/notification/Notification.store';
import { socketMiddleware } from 'domains/websockets/Websocket.middleware';
import WebSocketReducer from 'domains/websockets/Websocket.store';
import { buildStore } from 'utils/store';

export function createAppStore() {
  const wsMiddleware = socketMiddleware();
  return buildStore(
    {
      calculator: CalculatorReducer,
      websockets: WebSocketReducer,
      notifications: NotificationReducer,
    },
    [CalculatorSagas],
    [wsMiddleware],
  );
}

export const globalStore = createAppStore();
export type RootState = ReturnType<typeof globalStore.getState>;
