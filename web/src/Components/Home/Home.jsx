import React from 'react';
import './Home.css';
import { useState } from 'react';

import { Link } from 'react-router-dom'
import app_logo from '../Assets/app_logo.png'
import report_icon from '../Assets/report_icon.png'
import user_icon from '../Assets/user_icon.png'

const App = () => {

    return (
        <div className="app-container">
            <div className='app_logo'>
                <img src={app_logo}></img>
            </div>
            <div className='boxes'>
                <Link to="/report-inquiry" className="box">
                    <img
                        src={report_icon}
                        alt="Image 1"
                        className="box-image"
                    />
                    <p className="box-text">Report Inquiry</p>
                </Link>
                <Link to="/login" className="box">
                    <img
                        src={user_icon}
                        alt="Image 2"
                        className="box-image"
                    />
                    <p className="box-text">Technician Login</p>
                </Link>
            </div>
        </div>
    );
};

export default App;
