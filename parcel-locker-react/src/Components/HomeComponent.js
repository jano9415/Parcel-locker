import React from 'react';
import TestService from '../Service/TestService';


const HomeComponent = () => {

    const test = () => {

        TestService.test3().then((response) => {
            console.log(response.data)
        },
        (error) => {
            console.log(error)
        }
        )

    }

    
    return (
        <div>
            <button onClick={test}>Próba gomb</button>
            
        </div>
    );
}

export default HomeComponent;