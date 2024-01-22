import React, { useEffect, useState } from 'react';
import MyNavbar from '../Navbar/Navbar'; 
import { ToastContainer,toast } from 'react-toastify';
import './Users.css'
import axios from 'axios';

function UsersPage() {
    const [users, setUsers] = useState([]);

    // Api ile iletişim kurararak kullanıcı verilerini çeker.
    const getUsers = async () => {
        try {
            const response = await axios.get('/api/v1/users'); 
            setUsers(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    // UsersPage isimli bileşen ilk kez render edildiğinde çağırılır
    useEffect(() => {
        getUsers();
    }, []);

    // Sayfada kullanıcı bilgilerinin sağında bulunan butona basıldığında çalışır, Api'ye istek atarak kullanıcının isEnabled değerinin değişmesini ister.
    const switchIsUserEnabled = async (index) => {
        try{
            const response = await axios.patch('/api/v1/users/' + users[index].userId)
            toast.success("User enable status has been changed")
            getUsers()
        }catch(error){
            if(error.response){
                toast.error(error.response.data.message)
            }else{
                console.log(error.response)
            }
        }


    }

    return (
        <>
            <MyNavbar pageName={"Users"} />
            <div className="card" style={{width : 1500}}>
                <div className="headers">
                    <div className="header column" style={{width: 200}}>Hospital Id Number</div>
                    <div className="header column" style={{width: 200}}>User Name</div>
                    <div className="header column" style={{width: 200}}>User Role</div>
                    <div className="header column" style={{width: 200}}>isEnabled</div>
                    <div className="header column" style={{width: 200}}>Operations</div>
                </div>
                <div className="values">
                    {users.map((user,index) => (
                        <div key={user.id} className="user">
                            <div className="value column" style={{width: 200}}>{user.hospitalIdNumber}</div>
                            <div className="value column" style={{width: 200}}>{user.fullName}</div>
                            <div className="value column" style={{width: 200}}>{user.role}</div>
                            <div className="value column" style={{width: 200}}>{user.enabled ? 'Yes' : 'No'}</div>
                            <div className="value column" style={{width: 200}}><button onClick={() => switchIsUserEnabled(index)} style={{backgroundColor : user.enabled ? 'red' : 'green'}}> {user.enabled ? 'Set User Disable' : 'Set User Enable'}</button></div>
                            {console.log(user.isEnabled)}
                        </div>
                    ))}
                </div>
            </div>
            <ToastContainer />
        </>
    );
}

export default UsersPage;
