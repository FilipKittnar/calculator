import React from 'react';
import { ADDITION, DIVISION, MULTIPLICATION, Operator, SUBTRACTION } from 'domains/calculator/Calculator.types';

export const getOperationValue = (operator: Operator) => {
  switch (operator) {
    case ADDITION:
      return <>+</>;
    case SUBTRACTION:
      return <>-</>;
    case MULTIPLICATION:
      return <>*</>;
    case DIVISION:
      return <>&divide;</>;
  }
};
