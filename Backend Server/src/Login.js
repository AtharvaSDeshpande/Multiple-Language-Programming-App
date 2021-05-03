import React from 'react'
import './Login.css'
import {auth,provider} from './firebase'
import { useStateValue } from './StateProvider'
import { actionTypes } from './reducer';
import Giphy from '../src/images/giphy.gif';
import { Button } from '@material-ui/core';
function Login() {
    const [{user},dispatch] = useStateValue();
    const signIn = async () => {
        await auth.signInWithPopup(
           provider
        ).then((result) =>
                {
                    dispatch({
                        type: actionTypes.SET_USER,
                        user: result.user,
                    });
                })
                .catch((error) => alert(error.message));
        
    };
    return (
        <div className = "login">
        <div className = "loginContainer">
            <div className = "loginLogo">
                <img src = {Giphy} alt = "Cout"/>
              
            </div>
            <div className = "loginText">
                <h1>Sign in to Cout Backend Server</h1>
            </div>
            <div className = "loginButton">
                 <Button onClick= {signIn} className = "button" >
                SignIn
            </Button>
            </div>
           
            
        </div>
        
    </div>
)
}

export default Login
