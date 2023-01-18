import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Alert, Snackbar } from '@mui/material';
import { getNotificationState, setSnackbarClosed } from 'domains/notification/Notification.store';

export const WarningSnackbar = () => {
  const dispatch = useDispatch();

  const { snackbarOpen, snackbarMessage } = useSelector(getNotificationState);

  function handleClose() {
    dispatch(setSnackbarClosed());
  }

  return (
    <Snackbar
      anchorOrigin={{
        vertical: 'top',
        horizontal: 'center',
      }}
      open={snackbarOpen}
      autoHideDuration={4000}
      onClose={handleClose}
    >
      <Alert onClose={handleClose} severity="error">
        {snackbarMessage}
      </Alert>
    </Snackbar>
  );
};
