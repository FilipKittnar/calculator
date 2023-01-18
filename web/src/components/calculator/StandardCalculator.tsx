import React from 'react';
import { useDispatch } from 'react-redux';
import BackspaceIcon from '@mui/icons-material/Backspace';
import { Box } from '@mui/material';
import { getOperationValue } from 'components/calculator/CalculatorOperators';
import {
  FunctionButton,
  NumberButton,
  ResultField,
  ResultFieldBottomPart,
  ResultFieldTopPart,
} from 'components/calculator/StandardCalculator.styled';
import {
  addDecimalSeparatorToEntry,
  addNumberToEntry,
  clearEntry,
  clearMemory,
  performCalculation,
  removeLastCharacterFromEntry,
  selectOperator,
} from 'domains/calculator/Calculator.store';
import {
  ADDITION,
  Calculator,
  DIVISION,
  MULTIPLICATION,
  Operator,
  SUBTRACTION,
} from 'domains/calculator/Calculator.types';
import { Flex } from 'UI';

export interface StandardCalculatorProps {
  calculator?: Calculator;
}

export const StandardCalculator = ({ calculator }: StandardCalculatorProps) => {
  const dispatch = useDispatch();

  const addNewNumberToEntry = (number: number) => {
    dispatch(addNumberToEntry({ number }));
  };

  const selectNewOperator = (operationType: Operator) => {
    dispatch(selectOperator({ operationType }));
  };

  const isEmpty = (value?: number | string) => {
    return value === undefined || value === null;
  };

  const onlyResultPresent =
    isEmpty(calculator?.entry) && isEmpty(calculator?.value) && isEmpty(calculator?.currentOperation);
  const resolveResultFieldTopPartValue = () => {
    return (
      <ResultFieldTopPart>
        <Box height={25}>
          <Flex justify={'flex-end'}>
            {calculator?.value && calculator.value}
            {calculator?.currentOperation && getOperationValue(calculator?.currentOperation)}
          </Flex>
        </Box>
      </ResultFieldTopPart>
    );
  };

  const resolveResultFieldBottomPartValue = () => {
    const value = onlyResultPresent ? calculator?.latestResult : calculator?.entry;
    return (
      <ResultFieldBottomPart>
        <Flex justify={'flex-end'}>{value}</Flex>
      </ResultFieldBottomPart>
    );
  };

  return (
    <Flex direction={'column'}>
      <Flex size={4} direction={'row'} minHeight={80}>
        <ResultField>
          <Flex direction={'column'}>
            {resolveResultFieldTopPartValue()}
            {resolveResultFieldBottomPartValue()}
          </Flex>
        </ResultField>
      </Flex>
      <Flex direction={'row'}>
        <Flex>
          <FunctionButton onClick={() => dispatch(clearMemory())}>C</FunctionButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => dispatch(clearEntry())}>CE</FunctionButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => dispatch(removeLastCharacterFromEntry())}>
            <BackspaceIcon />
          </FunctionButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => selectNewOperator(DIVISION)}>&divide;</FunctionButton>
        </Flex>
      </Flex>
      <Flex direction={'row'}>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(7)}>7</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(8)}>8</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(9)}>9</NumberButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => selectNewOperator(MULTIPLICATION)}>X</FunctionButton>
        </Flex>
      </Flex>
      <Flex direction={'row'}>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(4)}>4</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(5)}>5</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(6)}>6</NumberButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => selectNewOperator(SUBTRACTION)}>-</FunctionButton>
        </Flex>
      </Flex>
      <Flex direction={'row'}>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(1)}>1</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(2)}>2</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => addNewNumberToEntry(3)}>3</NumberButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => selectNewOperator(ADDITION)}>+</FunctionButton>
        </Flex>
      </Flex>
      <Flex direction={'row'}>
        <Flex size={2}>
          <NumberButton onClick={() => addNewNumberToEntry(0)}>0</NumberButton>
        </Flex>
        <Flex>
          <NumberButton onClick={() => dispatch(addDecimalSeparatorToEntry())}>,</NumberButton>
        </Flex>
        <Flex>
          <FunctionButton onClick={() => dispatch(performCalculation())}>=</FunctionButton>
        </Flex>
      </Flex>
    </Flex>
  );
};
