type rec cssSelector =
  | NoSelector
  | All
  | Custom(string)
  | WithTag(string)
  | After
  | Before
  | Checked
  | Disabled
  | Indeterminate
  | FocusVisible
  | Focus
  | Hover
  | Active
  | Or
  | Not(cssSelector)
  | WithId(string)
  | WithClass(string)
  | RightAfter
  | AllAfter
  | WithAttr(string)
  | Child
  | ChildWithClass(string)
  | Descendant
  | DescendantWithClass(string)

let addSelectors = (
  ~cssSelectors: list<cssSelector>,
  style: ReactDOM.Style.t,
): ReactDOM.Style.t => {
  let rec typeIntoString = (cssSelector: cssSelector): string => {
    switch cssSelector {
    | NoSelector => ""
    | All => "*"
    | Custom(string) => string
    | WithTag(tag) => tag
    | After => ":after"
    | Before => ":before"
    | Checked => ":checked"
    | Disabled => ":disabled"
    | Indeterminate => ":indeterminate"
    | FocusVisible => ":focus-visible"
    | Focus => ":focus"
    | Hover => ":hover"
    | Active => ":active"
    | Or => ", &"
    | Not(cssSelector) => `:not(${cssSelector->typeIntoString})`
    | WithId(id) => `#${id}`
    | WithClass(class) => `.${class}`
    | RightAfter => `+`
    | AllAfter => `~`
    | Child => `>`
    | ChildWithClass(class) => `>.${class}`
    | Descendant => ` `
    | DescendantWithClass(class) => ` .${class}`
    | WithAttr(attr) => `[${attr}]`
    }
  }

  list{
    (
      `&${cssSelectors->Belt.List.reduce("", (acc, cssSelector) =>
          acc ++ typeIntoString(cssSelector)
        )}`,
      style,
    ),
  }
  ->Js.Dict.fromList
  ->Obj.magic
}

let styleWithSelectors = (
  ~cssSelectors: list<cssSelector>,
  style: ReactDOM.Style.t,
): ReactDOM.Style.t => style->addSelectors(~cssSelectors)
