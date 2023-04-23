import React, { useState } from 'react';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from '../Service/AuthService';

const LoginComponent = () => {

    let navigate = useNavigate();

    const form = useRef();

    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    //Email cím kiolvasása az input mezőből
    const onChangeEmailAddress = (e) => {
        const emailAddress = e.target.value;
        setEmailAddress(emailAddress);
    }

    //Jelszó kiolvasása az input mezőből
    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };

    //Bejelentkezés kezelése
    const handleLogin = (e) => {
        e.preventDefault();

        setMessage("");
        setLoading(true);

        
        AuthService.logIn(emailAddress, password).then(
            () => {
                navigate("/");
                window.location.reload();
            },
            (error) => {
                const resMessage = error.response.data
                console.log(error)

                setLoading(false);
                setMessage(resMessage)

            }
        )
        

    }


    return (
        <div>
            <div className="card col-md-6 offset-md-3 offset-md-3 mt-2">
                <div className="card card-container">
                    <form onSubmit={handleLogin} ref={form}>
                        <div className="form-group">
                            <label className='form-label' htmlFor="username">Email cím</label>
                            <input
                                type="text"
                                className="form-control mt-1"
                                name="username"
                                value={emailAddress}
                                placeholder="Felhasználói név"
                                required
                                onChange={onChangeEmailAddress}
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="password">Jelszó</label>
                            <input
                                type="password"
                                className="form-control mt-1"
                                name="password"
                                value={password}
                                placeholder="Jelszó"
                                required
                                onChange={onChangePassword}
                            />
                        </div>

                        <div className="form-group m-2">
                            <button className="btn btn-primary btn-block mt-2" disabled={loading}>
                                {loading && (
                                    <span className="spinner-border spinner-border-sm"></span>
                                )}
                                <span>Bejelentkezés</span>
                            </button>
                        </div>

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
    );
}

export default LoginComponent;