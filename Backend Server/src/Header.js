import { Avatar, Tooltip } from '@material-ui/core'
import React from 'react'
import './Header.css'
import { useStateValue } from './StateProvider';
import logo from './images/logo.png'
import { actionTypes } from './reducer';
import TableChartOutlinedIcon from '@material-ui/icons/TableChartOutlined';
function Header({showNav}) {
    const [{user},dispatch] = useStateValue();
    const logout = () => {
        dispatch({
            type: actionTypes.SET_USER,
            user: null,
        });
    }
    return (
        <div className = "header">
            <div className = "image"><Avatar className = "logo" src = {logo} alt = "cout-logo" /></div>
            <h1>COUT BACKEND SERVER</h1>
            <div  className = "avatar">
                {showNav?(
                <div  className = "avatar">
                    <Tooltip title = "Languages">
                        <a href = "https://firebasestorage.googleapis.com/v0/b/cout-1c9d9.appspot.com/o/cout%20json%20-%20Sheet1.pdf?alt=media&token=bffbac95-c42b-41c6-bb84-b0d94c014f08" target = "_blank" rel="noreferrer">
                            <TableChartOutlinedIcon className = "tableIcon"> </TableChartOutlinedIcon>
                        </a>
                        
                    </Tooltip>
                    <Tooltip title = "Logout">
                        <Avatar className = "profilePic" src = {user?.photoURL} onClick = {logout}></Avatar>
                    </Tooltip>
                </div>   
                ):(<div></div>)}
                   
            </div>
        </div>
    )
}

export default Header
