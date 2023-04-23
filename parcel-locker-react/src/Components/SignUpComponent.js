import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { Link, useNavigate } from 'react-router-dom';

const SignUpComponent = () => {


    const [emailAddress, setEmailAddress] = useState("")
    const [password, setPassword] = useState("")
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [message, setMessage] = useState("");

    let navigate = useNavigate();

    useEffect(() => {

    }, [])

    const signUp = (e) => {
        e.preventDefault();
        AuthService.signUp(emailAddress, password, firstName, lastName).then((response) => {
            navigate("/login");
            window.location.reload();

        },
            (error) => {
                const resMessage = error.response.data

                setMessage(resMessage)
            })
    }




    return (
        <div>
            <div className='container m-3'>
                <div className='container'>
                    <div className='card col-md-6 offset-md-3 offset-md-3'>
                        <h3 className='text-center'>Új felhasználó hozzáadása</h3>
                        <div className='card-body'>
                            <form>
                                <div className='form-group'>
                                    <label className='form-label'>Email cím</label>
                                    <input
                                        type="text"
                                        placeholder='Email cím'
                                        name='partName'
                                        className='form-control'
                                        value={emailAddress}
                                        onChange={(e) => setEmailAddress(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Jelszó</label>
                                    <input
                                        type="password"
                                        placeholder='Jelszó'
                                        name='maxPieceInBox'
                                        className='form-control'
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Vezetéknév</label>
                                    <input
                                        type="text"
                                        placeholder='Vezetéknév'
                                        name='maxPieceInBox'
                                        className='form-control'
                                        value={lastName}
                                        onChange={(e) => setLastName(e.target.value)} />
                                </div>
                                <div className='form-group'>
                                    <label className='form-label'>Keresztnév</label>
                                    <input
                                        type="text"
                                        placeholder='Keresztnév'
                                        name='maxPieceInBox'
                                        className='form-control'
                                        value={firstName}
                                        onChange={(e) => setFirstName(e.target.value)} />
                                </div>
                                <button className='btn btn-success m-2' onClick={(e) => signUp(e)}>Mentés</button>
                                <Link to="/">
                                    <button className='btn btn-danger'>Mégse</button>
                                </Link>
                                {message && (
                                    <div className="form-group">
                                        <div className="alert alert-danger" role="alert">
                                            {message}
                                        </div>
                                    </div>
                                )}
                            </form>

                        </div>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default SignUpComponent;