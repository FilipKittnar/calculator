open Emotion
open ReactDOM

let container =
  Style.make(~border="solid 1px #3a4655", ~boxShadow="0 8px 50px -7px black", ())->styleToClass
let section = Style.make(~border="solid 1px", ())->styleToClass
let subSection = Style.make(~borderBottom="dotted 1px", ())->styleToClass
