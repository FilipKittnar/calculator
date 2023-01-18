import { configureStore, EnhancedStore } from '@reduxjs/toolkit';
import { ThunkMiddlewareFor } from '@reduxjs/toolkit/src/getDefaultMiddleware';
import { Action, AnyAction, Dispatch, Middleware, Reducer, ReducersMapObject } from 'redux';
import createSagaMiddleware, { Saga } from 'redux-saga';

type Middlewares<S> = ReadonlyArray<Middleware<unknown, S>>;

export function buildStore<S = any, A extends Action = AnyAction, M extends Middlewares<S> = [ThunkMiddlewareFor<S>]>(
  reducers: Reducer<S, A> | ReducersMapObject<S, A>,
  sagas: Saga[],
  middleware?: M,
): EnhancedStore<S, A, Middleware<unknown, S, Dispatch<AnyAction>>[]> {
  const sagaMiddleware = createSagaMiddleware();
  const middlewareToAdd = middleware ? middleware.concat(sagaMiddleware) : [sagaMiddleware];

  const store = configureStore({
    reducer: reducers,
    middleware: middlewareToAdd,
  });

  sagas.forEach((saga: Saga) => sagaMiddleware.run(saga));
  return store;
}
