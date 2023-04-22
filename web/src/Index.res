open ReactDOM

%%raw(`import "./index.css"`)

querySelector("#root")
->Belt.Option.getExn
->Client.createRoot
->Client.Root.render(
  <React.StrictMode>
    <Root />
  </React.StrictMode>,
)
