import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import DeleteIcon from '@mui/icons-material/Delete';
import { Box } from '@mui/material';
import {
  CalculatorHistoryContainer,
  HistoryHeader,
  RowsContainer,
  SIconButton,
} from 'components/calculator/CalculatorHistory.styled';
import { getOperationValue } from 'components/calculator/CalculatorOperators';
import { clearHistory, getHistoryRows, removeHistoryRow } from 'domains/calculator/Calculator.store';
import { HistoryRow } from 'domains/calculator/Calculator.types';
import { Flex } from 'UI';

const formatRow = (row: HistoryRow) => {
  return (
    <Flex size={4}>
      {row.value} {getOperationValue(row.operation)} {row.incomingValue} = {row.result}
    </Flex>
  );
};
export const CalculatorHistory = () => {
  const dispatch = useDispatch();
  const rows = useSelector(getHistoryRows);

  return (
    <CalculatorHistoryContainer>
      <HistoryHeader>Calculator history</HistoryHeader>
      <Box height={10} />

      <Flex align={'center'}>
        <SIconButton onClick={() => dispatch(clearHistory())} disabled={rows === undefined || rows.length === 0}>
          <DeleteIcon fontSize={'small'} />
        </SIconButton>
        Clear history
      </Flex>
      <Box height={20} />

      <RowsContainer>
        {rows?.map((row, index) => (
          <Flex key={index} direction={'row'} align={'center'} justify={'center'}>
            <Flex size={1}>
              <SIconButton onClick={() => dispatch(removeHistoryRow({ rowId: row.rowId }))}>
                <DeleteIcon fontSize={'small'} />
              </SIconButton>
            </Flex>
            {formatRow(row)}
          </Flex>
        ))}
      </RowsContainer>
    </CalculatorHistoryContainer>
  );
};
