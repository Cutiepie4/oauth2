// src/Home.js
import axios from 'axios';
import React, { useState, useEffect } from 'react';

const Home = () => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState('');

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const accessToken = params.get('access_token');
        const refreshToken = params.get('refresh_token');

        sessionStorage.setItem('access_token', accessToken);
        sessionStorage.setItem('refresh_token', refreshToken);
        setToken(accessToken);

        axios.get('https://api.github.com/user', {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        })
            .then(res => {
                setUser(res.data);
                console.log(res.data);
            })
            .catch((error) => console.error('Error fetching user data:', error));
    }, []);

    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            {user ? (
                <div>
                    <h1>Welcome, {user.login}</h1>
                    <img src={user.avatar_url} alt="User Avatar" width="100" />
                    <p>{user.bio}</p>
                    <p>Token: {token}</p>
                </div>
            ) : (
                <h1>Loading...</h1>
            )}
        </div>
    );
};

export default Home;
