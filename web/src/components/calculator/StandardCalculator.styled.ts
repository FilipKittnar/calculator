import { Box, Button } from '@mui/material';
import styled from 'styled-components';

export const ResultField = styled(Box)`
  padding: 20px;
  width: 100%;
  background: #3a4655;
  border: solid 1px #3a4655;
`;

const SButton = styled(Button)`
  && {
    width: 100%;
    border: solid 1px #4a5562;
    border-radius: 0;
    font-size: 18px;
    color: white;

    :hover {
      background-color: #9baab9;
    }
  }
`;

export const ResultFieldTopPart = styled.div`
  border-bottom: dotted 1px;
  height: 40%;
  margin-bottom: 10px;
  width: 100%;
`;

export const ResultFieldBottomPart = styled.div`
  height: 60%;
  width: 100%;
  font-weight: bold;
  font-size: 20px;
`;
export const NumberButton = styled(SButton)`
  && {
    background-color: #4b5c70;
  }
`;

export const FunctionButton = styled(SButton)`
  && {
    background-color: #425062;
  }
`;
