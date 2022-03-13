import logo from './logo.svg';
import './App.css';
import React, { useState }  from 'react';
import axios from 'axios';
import ReactHtmlParser, { processNodes, convertNodeToElement, htmlparser2 } from 'html-react-parser';


function App() {
  const [message, setMessage] = useState("LOADING_THE_MESSAGE...");
  React.useEffect(effect => {
    axios.get(`/hello`)
    .then(res => {
      console.log(res);
      console.log(res.data);
      setMessage(res.data);
    })
  }, []);



  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div>
          Wiadomość z backendu: [<code><div> { ReactHtmlParser (message) } </div></code>].
        </div>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Superanckoooo
        </a>
      </header>
    </div>
  );
}

export default App;
