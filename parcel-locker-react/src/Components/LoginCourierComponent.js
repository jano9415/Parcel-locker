import React, { useEffect, useState } from 'react';
import AuthService from '../Service/AuthService';
import { useNavigate } from 'react-router-dom';

const LoginCourierComponent = () => {

    const [uniqueCourierId, setUniqueCourierId] = useState("");
    const [message, setMessage] = useState("");

    let navigate = useNavigate();

    useEffect(() => {
        serialRead()


    }, [])



    //Soros port olvasása
    //A 10 karakteres rfid kártya uid-t bájtonként olvasom be. Ezekből a karakterekből csinálok egy string-et
    const serialRead = async () => {
        let uId = ""
        let counter = 0;

        try {
            const port = await navigator.serial.requestPort();
            await port.open({ baudRate: 9600 });

            // eslint-disable-next-line no-undef
            const decoder = new TextDecoderStream();

            port.readable.pipeTo(decoder.writable);

            const inputStream = decoder.readable;
            const reader = inputStream.getReader();


            while (counter < 10) {
                const { value, done } = await reader.read();
                uId += value
                console.log("Ez az uid: " + uId)

                if (value) {
                    console.log("Ez a value: " + value);
                    counter++
                    console.log("A számláló: " + counter)

                }
                if (done) {
                    console.log('[readLoop] DONE', done);
                    reader.releaseLock();
                    break;
                }
            }
            setUniqueCourierId(uId)
            counter = 0
            uId = ""

        } catch (error) {
            console.log("A serial nem elérhető!");
        }
    }

    const logIn = () => {
        AuthService.courierLogin(uniqueCourierId).then(
            () => {
                navigate("/");
                window.location.reload();
            },
            (error) => {
                const resMessage = error.response.data

                setMessage(resMessage)

            }
        )
    }


    return (
        <div>
            <div>
                <h3>Érintsd a kártyádat az olvasóhoz, majd kattints a bejelentkezés gombra</h3>
            </div>
            <button className="btn btn-primary btn-block" onClick={logIn}>Bejelentkezés</button>
            {
                uniqueCourierId && (
                    <div className="form-group">
                        <div className="alert alert-success" role="alert">
                            <span>Kártya beolvasva</span>
                        </div>
                    </div>
                )
            }
            {message && (
                <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                        {message}
                    </div>
                </div>
            )}

        </div>
    );
}

export default LoginCourierComponent;