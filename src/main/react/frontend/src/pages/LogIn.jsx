import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/LogIn.css";
import DOMPurify from 'dompurify';

function LogIn () {

    const BASE_URL = "http://localhost:8080";

    const navigate = useNavigate();

    const [formData, setFormData] = useState({ name: '', password: '' });
    const [statusMessage, setStatusMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleOnChange = (e) => {
        setFormData({
            ...formData, //copia todo lo que ya existiera en formData
            [e.target.name]: e.target.value //añade a formData "nombre_campo: "lo_escrito"
        });
    }

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setStatusMessage('Loading');

        const cleanUsername = DOMPurify.sanitize(formData.name);
        const cleanPassword = DOMPurify.sanitize(formData.password);

        try {
            const response = await fetch(`${BASE_URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' }, 
                body: JSON.stringify({
                    name: cleanUsername,
                    password: cleanPassword
                })
            });

            const body = await response.json().catch(() => null)

            if(!response.ok) {
                setStatusMessage(body?.message || `Error: ${response.status}`);
                return;
            } 

            setStatusMessage("Log in successful!");
            navigate(`/user`);
        } catch(err) {
            setStatusMessage("Network error. Please, try again");
            console.log("Network error", err);
        } finally {
            setIsLoading(false);
        }
    }
    return <div className="login">
        <h3 className="login-title">Log In</h3>
        <form onSubmit={handleOnSubmit} className="login-form">
            <input 
                type="text" 
                placeholder="Username"
                className="user-name"
                name="name"
                value={formData.name}
                autoComplete="username"
                onChange={handleOnChange}
                required/>
            <input 
                type="password" 
                placeholder="Password"
                className="user-pswd"
                name="password"
                value={formData.password}
                autoComplete="current-password"
                onChange={handleOnChange}
                required/>
            <button type="submit" className="login-button" disabled={isLoading}>
                {isLoading ? 'Loading...' : 'Log in'}
            </button>
            {statusMessage && <p className="status-text">{statusMessage}</p>}
        </form>
        <button type="submit" className="signin-button">
            <Link to="/sign-in" className="signin-link">Sign In</Link>
        </button>
    </div>
}

export default LogIn