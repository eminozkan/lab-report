import React, { useState } from 'react';
import './Navbar.css';
import { Link , useNavigate} from 'react-router-dom'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify'

import app_logo from '../Assets/app_logo.png'

const MyNavbar = ({ pageName }) => {
  const [isUserAdminRole, setIsUserAdminRole] = useState('')
  const navigate = useNavigate()
  const logout = async () => {
    try {
      const response = await axios.get("/api/v1/auth/logout")
      toast.success("You are redirecting to home page")
      setTimeout(() => {
        navigate("/")
      },2000)
    } catch (error) {
      console.log(error)
    }
  }

  
  const hasUserAuthenticationCookie = async () => {
    try {
        const response = await axios.get("/api/v1/session")
        if(response.data.userRole === "ROLE_MANAGER"){
          setIsUserAdminRole(true)
        }else{
          setIsUserAdminRole(false)
        }
    } catch (error) {
        toast.error("Unauthenticated User. Redirecting to Home Page")
        setTimeout(() => {
            navigate("/")
        }, 2000)
    }
}

hasUserAuthenticationCookie()


  return (
    <div className="navbar">
      <div className="logo"><img src={app_logo}></img></div>
      <div className="pageName">{pageName}</div>
      <div className="nav-links">
        <Link to="/users" style={{ display: isUserAdminRole ? 'block' : 'none' }} className='link'> Users </Link>
        <Link to="/reports" className='link'>Reports</Link>
        <Link to="/add-report" className='link'>Add Report</Link>
        <div className="logout">
          <div id='logout' onClick={() => logout()}>Log out</div>
        </div>
      </div>
    </div>
  );
}

export default MyNavbar;
