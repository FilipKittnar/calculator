import React from 'react';
import { enableMapSet } from 'immer';
import { createRoot } from 'react-dom/client';
import { App } from 'app/App';
import './index.css';

enableMapSet();

const rootElement = document.getElementById('root') as HTMLElement;
const root = createRoot(rootElement);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
