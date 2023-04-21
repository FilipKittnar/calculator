@react.component
let make = () => {
  <Context.Provider>
    <Mui.ThemeProvider theme={Theme.theme()}>
      <Mui.CssBaseline />
      <Mui.StylesProvider injectFirst=true>
        <App />
      </Mui.StylesProvider>
    </Mui.ThemeProvider>
  </Context.Provider>
}
