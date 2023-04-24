// import React, { useEffect } from 'react';

// import { useDispatch, useSelector } from 'react-redux';
// import Backdrop from '@mui/material/Backdrop';
// import CircularProgress from '@mui/material/CircularProgress';
// import { CalculatorContainer } from 'components/calculator/Calculator.styled';
// import { CalculatorHistory } from 'components/calculator/CalculatorHistory';
// import { StandardCalculator } from 'components/calculator/StandardCalculator';
// import { fetchCalculator, getCalculator } from 'domains/calculator/Calculator.store';
// import { Flex } from 'UI';
// import { getData, LOADING } from 'utils/dataStatus';

// export const Calculator = () => {
//   const dispatch = useDispatch();
//   const calculator = useSelector(getCalculator);

//   const [loading, setLoading] = React.useState(false);

//   useEffect(() => {
//     setLoading(calculator === LOADING);
//   }, [calculator, setLoading]);

//   useEffect(() => {
//     if (calculator === undefined) {
//       dispatch(fetchCalculator());
//     }
//   }, [dispatch, calculator]);

//   return (
//     <CalculatorContainer>
//       <Flex direction={'row'}>
//         <StandardCalculator calculator={getData(calculator)} />
//         <CalculatorHistory />
//       </Flex>
//       <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
//         <CircularProgress color="inherit" />
//       </Backdrop>
//     </CalculatorContainer>
//   );
// };

// export const CalculatorContainer = styled.div`
//   border: solid 1px #3a4655;
//   box-shadow: 0 8px 50px -7px black;
// `;

open Emotion
open ReactDOM
open Mui

module Types = Calculator_Types
module Standard = Calculator_Standard

module Classes = {
  let container =
    Style.make(~border="solid 1px #3a4655", ~boxShadow="0 8px 50px -7px black", ())->styleToClass
}

@react.component
let make = () => {
  let (state, dispatch) = React.useContext(Storage.Context.t)

  React.useEffect0(() => {
    Constants.Calculator.fetchCalculatorUrl->WebSocket.send(~state, ~dispatch)

    None
  })

  React.useEffect1(() => {
    state.calculator->Belt.Option.forEach(calculator =>
      switch calculator {
      | Error({?errorType}) =>
        dispatch(
          SetSnackbarOpen(
            switch errorType {
            | Some(ZeroDivisionAttempted) => "Cannot divide with zero"
            | Some(MissingOperation) => "Operation not selected"
            | Some(MissingEntry) => "Please fill in value"
            | None => "Unexpected error"
            },
          ),
        )
      | Ok(_) => ()
      }
    )

    None
  }, [state.calculator])

  <div className=Classes.container>
    <Grid container=true direction=#row>
      <Standard
        calculator={state.calculator
        ->Belt.Option.map(calculator => calculator->Belt.Result.getWithDefault({results: list{}}))
        ->Belt.Option.getWithDefault({results: list{}})}
      />
    </Grid>
  </div>
}
