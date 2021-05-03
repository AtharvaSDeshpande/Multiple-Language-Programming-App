import React from 'react'
import QuestionBlock from './QuestionBlock'
import './App.css'
import { useStateValue } from './StateProvider';
import Login from './Login';
import Header from './Header';
function App() {
  const [{user},dispatch] = useStateValue();

  return (
    <div className="app">
      <Header showNav = {user!=null}/>
      {user?(<QuestionBlock/>):(<Login/>)}
    </div>
   );
}
export default App;