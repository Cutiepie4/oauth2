// src/Login.js
import React from 'react';

const Login = () => {
    const handleLogin = () => {
        window.location.href = 'http://localhost:8080/oauth2/authorization/github';
    };

    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h1>Login with GitHub</h1>
            <button onClick={handleLogin}>Login with GitHub</button>
        </div>
    );
};

export default Login;
