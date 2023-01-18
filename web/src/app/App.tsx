import React from 'react';
import { Provider, ReactReduxContext } from 'react-redux';
import { SnackbarProvider } from 'notistack';
import { AppContainer } from 'app/AppContainer';
import { globalStore } from './AppStore';

export const App = () => {
  return (
    <Provider store={globalStore} context={ReactReduxContext}>
      <SnackbarProvider maxSnack={3}>
        <AppContainer />
      </SnackbarProvider>
    </Provider>
  );
};
