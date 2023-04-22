@react.component
let make = () => {
  <Storage.Provider>
    <Mui.ThemeProvider theme={Theme.theme()}>
      <Mui.CssBaseline />
      <Mui.StylesProvider injectFirst=true>
        <App />
      </Mui.StylesProvider>
    </Mui.ThemeProvider>
  </Storage.Provider>
}
