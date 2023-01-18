import React from 'react';
import { useDispatch } from 'react-redux';
import { Box } from '@mui/material';
import { SContainer } from 'app/AppContainer.styled';
import { Calculator } from 'components/calculator/Calculator';
import { WarningSnackbar } from 'components/warningSnackbar/WarningSnackbar';
import { wsConnect } from 'domains/websockets/Websocket.store';

export const AppContainer = () => {
  const dispatch = useDispatch();
  dispatch(wsConnect());

  return (
    <SContainer justify={'center'} align={'center'} direction={'column'}>
      <WarningSnackbar />
      <Box height={50} />
      <Box>
        <Calculator />
      </Box>
    </SContainer>
  );
};
