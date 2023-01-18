import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { createSelector } from 'reselect';
import { APP_NAMESPACE } from 'app/App.constants';
import { RootState } from 'app/AppStore';
import { NotificationState } from 'domains/notification/Notification.types';

const initialState: NotificationState = {
  snackbarOpen: false,
  snackbarMessage: undefined,
};

const slice = createSlice({
  name: `${APP_NAMESPACE}/NOTIFICATION`,
  initialState,
  reducers: {
    setSnackbarOpen: (state, action: PayloadAction<string>) => {
      state.snackbarOpen = true;
      state.snackbarMessage = action.payload;
    },
    setSnackbarClosed: (state) => {
      state.snackbarOpen = false;
    },
  },
});

export const { setSnackbarOpen, setSnackbarClosed } = slice.actions;

export const getNotificationState = createSelector(
  (state: RootState) => state.notifications,
  (state) => state,
);

export default slice.reducer;
