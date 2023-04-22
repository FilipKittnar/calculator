// <Flex direction={'column'}>
//       <Flex size={4} direction={'row'} minHeight={80}>
//         <ResultField>
//           <Flex direction={'column'}>
//             {resolveResultFieldTopPartValue()}
//             {resolveResultFieldBottomPartValue()}
//           </Flex>
//         </ResultField>
//       </Flex>
//       <Flex direction={'row'}>
//         <Flex>
//           <FunctionButton onClick={() => dispatch(clearMemory())}>C</FunctionButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => dispatch(clearEntry())}>CE</FunctionButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => dispatch(removeLastCharacterFromEntry())}>
//             <BackspaceIcon />
//           </FunctionButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => selectNewOperator(DIVISION)}>&divide;</FunctionButton>
//         </Flex>
//       </Flex>
//       <Flex direction={'row'}>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(7)}>7</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(8)}>8</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(9)}>9</NumberButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => selectNewOperator(MULTIPLICATION)}>X</FunctionButton>
//         </Flex>
//       </Flex>
//       <Flex direction={'row'}>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(4)}>4</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(5)}>5</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(6)}>6</NumberButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => selectNewOperator(SUBTRACTION)}>-</FunctionButton>
//         </Flex>
//       </Flex>
//       <Flex direction={'row'}>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(1)}>1</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(2)}>2</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => addNewNumberToEntry(3)}>3</NumberButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => selectNewOperator(ADDITION)}>+</FunctionButton>
//         </Flex>
//       </Flex>
//       <Flex direction={'row'}>
//         <Flex size={2}>
//           <NumberButton onClick={() => addNewNumberToEntry(0)}>0</NumberButton>
//         </Flex>
//         <Flex>
//           <NumberButton onClick={() => dispatch(addDecimalSeparatorToEntry())}>,</NumberButton>
//         </Flex>
//         <Flex>
//           <FunctionButton onClick={() => dispatch(performCalculation())}>=</FunctionButton>
//         </Flex>
//       </Flex>
//     </Flex>

open Mui
open ReactDOM
open Emotion

module Classes = {
  let resultField =
    Style.make(
      ~padding="20px",
      ~width="100%",
      ~background="#3a4655",
      ~border="solid 1px #3a4655",
      (),
    )->styleToClass
  let resultFieldTopPart =
    Style.make(
      ~borderBottom="dotted 1px",
      ~height="40%",
      ~marginBottom="10px",
      ~width="100%",
      (),
    )->styleToClass
  let resultFieldBottomPart =
    Style.make(~height="60%", ~width="100%", ~fontWeight="bold", ~fontSize="20px", ())->styleToClass
}

module ResultFieldTopPartValue = {
  @react.component
  let make = (~calculator: option<Calculator_Types.t>=?) => {
    <Grid item=true className=Classes.resultFieldTopPart>
      <Grid container=true justify=#"flex-end">
        {calculator
        ->Belt.Option.flatMap(({?value, _}) => value)
        ->Belt.Option.map(Belt.Int.toString)
        ->Belt.Option.getWithDefault("")
        ->Jsx.string}
        {calculator
        ->Belt.Option.flatMap(({?currentOperation, _}) => currentOperation)
        ->Belt.Option.map(Calculator_Types.Operator.toElement)
        ->Belt.Option.getWithDefault(Jsx.null)}
      </Grid>
    </Grid>
  }
}

module ResultFieldBottomPartValue = {
  @react.component
  let make = (~calculator: option<Calculator_Types.t>=?) => {
    let onlyResultPresent = () =>
      calculator->Belt.Option.flatMap(({?entry, _}) => entry)->Belt.Option.isNone &&
      calculator->Belt.Option.flatMap(({?value, _}) => value)->Belt.Option.isNone &&
      calculator
      ->Belt.Option.flatMap(({?currentOperation, _}) => currentOperation)
      ->Belt.Option.isNone

    let getValue = () =>
      (
        onlyResultPresent()
          ? calculator
            ->Belt.Option.flatMap(({?latestResult, _}) => latestResult)
            ->Belt.Option.map(Belt.Int.toString)
          : calculator->Belt.Option.flatMap(({?entry, _}) => entry)
      )->Belt.Option.getWithDefault("")

    <Grid item=true className=Classes.resultFieldBottomPart>
      <Grid container=true justify=#"flex-end"> {getValue()->Jsx.string} </Grid>
    </Grid>
  }
}

@react.component
let make = (~calculator=?) => {
  <Grid container=true direction=#column>
    <Grid item=true>
      <Grid container=true direction=#row>
        <Grid item=true className=Classes.resultField>
          <Grid container=true direction=#column>
            <ResultFieldTopPartValue ?calculator />
            <ResultFieldBottomPartValue ?calculator />
          </Grid>
        </Grid>
      </Grid>
    </Grid>
  </Grid>
}