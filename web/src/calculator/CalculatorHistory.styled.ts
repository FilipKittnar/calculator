import { IconButton } from '@mui/material';
import styled from 'styled-components';

export const CalculatorHistoryContainer = styled.div`
  padding: 20px;
  width: 300px;
  background: #3a4655;
  border: solid 1px #4a5562;
`;

export const HistoryHeader = styled.div`
  font-size: 20px;
`;

export const RowsContainer = styled.div`
  width: 300px;
  height: 200px;
  overflow-y: auto;
  border: solid 1px #4a5562;
`;

export const SIconButton = styled(IconButton)`
  && {
    color: white;
  }
`;
