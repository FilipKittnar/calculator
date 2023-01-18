import React, { useEffect } from 'react';

import { useDispatch, useSelector } from 'react-redux';
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import { CalculatorContainer } from 'components/calculator/Calculator.styled';
import { CalculatorHistory } from 'components/calculator/CalculatorHistory';
import { StandardCalculator } from 'components/calculator/StandardCalculator';
import { fetchCalculator, getCalculator } from 'domains/calculator/Calculator.store';
import { Flex } from 'UI';
import { getData, LOADING } from 'utils/dataStatus';

export const Calculator = () => {
  const dispatch = useDispatch();
  const calculator = useSelector(getCalculator);

  const [loading, setLoading] = React.useState(false);

  useEffect(() => {
    setLoading(calculator === LOADING);
  }, [calculator, setLoading]);

  useEffect(() => {
    if (calculator === undefined) {
      dispatch(fetchCalculator());
    }
  }, [dispatch, calculator]);

  return (
    <CalculatorContainer>
      <Flex direction={'row'}>
        <StandardCalculator calculator={getData(calculator)} />
        <CalculatorHistory />
      </Flex>
      <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
        <CircularProgress color="inherit" />
      </Backdrop>
    </CalculatorContainer>
  );
};
