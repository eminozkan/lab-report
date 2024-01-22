import React, { useState } from 'react';
import './LoginSignup.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import left_arrow from '../Assets/left_arrow.png'
import { Link , useNavigate} from 'react-router-dom'

const LoginSignup = () => {
    const [action, setAction] = useState('Login');
    const [fullName, setName] = useState('');
    const [hospitalIdNumber, setHospitalIdNumber] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate()


    const handleLoginSignup = async () => {

        try {
            if (action === 'Login') {
                const response = await axios.post("/api/v1/auth/login", {
                    hospitalIdNumber,
                    password,
                });
                toast.success(response.data.message)
                setTimeout(() => {
                    navigate("/reports")
                },2000)
            } else if (action === 'Sign Up') {
                const response = await axios.post('/api/v1/register', {
                    fullName,
                    hospitalIdNumber,
                    password,
                });
                toast.success(response.data.message)
            }
        } catch (error) {
            if (error.response) {
                toast.error(error.response.data.message)
            } else if (error.request) {
                console.log(error.request)
            }
        }
    };

    return (
        <div className='container'>

            <div className="card">
                <Link to="/" className="home">
                    <img src={left_arrow} className='arrow' />
                    <div className="back">
                        Back To Home Page
                    </div>
                </Link>
                <div className="switch active">
                    <div
                        className={action === 'Sign Up' ? 'active' : 'gray'}
                        onClick={() => {
                            setAction('Login');
                            clearValues()
                        }}
                    >
                        Login
                    </div>
                    <div
                        className={action === 'Login' ? ' active' : 'gray'}
                        onClick={() => {
                            setAction('Sign Up');
                            clearValues()
                        }}
                    >
                        Sign Up
                    </div>
                </div>
                <div className="header">
                    <div className="text">{action}</div>
                    <div className="underline"></div>
                </div>
                <div className="inputs">
                    {action === 'Login' ? null : (
                        <>
                            <div className="input">
                                <input
                                    type="text"
                                    placeholder="Name"
                                    id="fullname"
                                    value={fullName}
                                    onChange={(e) => setName(e.target.value)}
                                />
                            </div>
                        </>
                    )}
                    <div className="input">
                        <input
                            type="number"
                            placeholder="Hospital Id Number"
                            id="hospitalIdNumber"
                            value={hospitalIdNumber}
                            onChange={(e) => setHospitalIdNumber(e.target.value)}
                        />
                    </div>
                    <div className="input">
                        <input
                            type="password"
                            placeholder="Password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                </div>
                <div className="submit-container">
                    <div
                        className='submit'
                        onClick={() => {
                            handleLoginSignup()

                        }}
                    >
                        {action}
                    </div>
                </div>
            </div>
            <ToastContainer  />
        </div>
    );
    function clearValues() {
        setHospitalIdNumber('')
        setName('')
        setPassword('')
    }
};



export default LoginSignup;
