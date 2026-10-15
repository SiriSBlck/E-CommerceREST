import { useState } from "react"
import { Link, useNavigate } from "react-router-dom";
import "../css/SignIn.css"
import DOMPurify from 'dompurify';

function SignIn () {

    const BASE_URL = "http://localhost:8080";

    const navigate = useNavigate();

    const [formData, setFormData] = useState({ name: '', password: '', confirmedPassword: '', email: '', role: '', address: '' });
    const matchingPaswords = formData.password === formData.confirmedPassword;
    const [statusMessage, setStatusMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleOnChange = (e) => {
        setFormData({
            ...formData, 
            [e.target.name]: e.target.value 
        });
    }

    const handleOnClick = (e) => {
        setFormData(prev => ({
            ...prev, 
            role: e.target.value
        }));
    }

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setStatusMessage('Loading');

        const cleanName = DOMPurify.sanitize(formData.name);
        const cleanPassword = DOMPurify.sanitize(formData.password);
        const cleanEmail = DOMPurify.sanitize(formData.email);
        const cleanAddress = DOMPurify.sanitize(formData.address);
        
        try {
            const response = await fetch(`${BASE_URL}/signin`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' }, 
                body: JSON.stringify({
                    name: cleanName,
                    password: cleanPassword,
                    email: cleanEmail,
                    role: formData.role,
                    address: cleanAddress,
                })
            });

            const body = await response.json().catch(() => null);

            if(!response.ok) {
                setStatusMessage(body?.message || `Error: ${response.status}`);
                return;
            }

            setStatusMessage("Sign in successful!");
            navigate(`/user`)
        } catch (err) {
            setStatusMessage("Network error. Please, try again");
            console.log("Network error", err);
        } finally {
            setIsLoading(false);
        }
    }

    return <div className="wrapper">
        <h3 className="signin-title">Sign In</h3>
        <form onSubmit={handleOnSubmit} className="login-form">
            <div className="elem">
                <p className="field-text">Name</p>
                <input 
                    type="text" 
                    placeholder="Name"
                    className="user-name"
                    name="name"
                    value={formData.name}
                    autoComplete="name"
                    onChange={handleOnChange}
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Password</p>
                <input 
                    type="password" 
                    placeholder="Password"
                    className="user-pswd"
                    name="password"
                    value={formData.password}
                    autoComplete="current-password"
                    onChange={handleOnChange}
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Confirm password</p>
                <input 
                    type="password" 
                    placeholder="Password"
                    className="user-pswd"
                    name="confirmedPassword"
                    value={formData.confirmedPassword}
                    autoComplete="current-password"
                    onChange={handleOnChange}
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Email</p>
                <input 
                    type="email" 
                    placeholder="Email"
                    className="user-email"
                    name="email"
                    value={formData.email}
                    autoComplete="email"
                    onChange={handleOnChange}
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Address</p>
                <input 
                    type="text" 
                    placeholder="Address"
                    className="user-address"
                    name="address"
                    value={formData.address}
                    autoComplete="address"
                    onChange={handleOnChange}/>
            </div>   
            {formData.password.length > 0 && !matchingPaswords 
                && <p>Las contraseñas no coinciden</p>}
            <button type="submit" className="signin-button-client" disabled={!matchingPaswords} value="ROLE_CLIENT" onClick={handleOnClick}>
                {isLoading ? 'Loading' : 'Sign in as client'}
            </button>
            <button type="submit" className="signin-button-supplier" disabled={!matchingPaswords} value="ROLE_SUPPLIER" onClick={handleOnClick}>
                {isLoading ? 'Loading' : 'Sign in as supplier'}
            </button>
            {statusMessage && <p className="status-text">{statusMessage}</p>}
        </form>
        <button type="submit" className="login-button">
            <Link to="/log-in" className="signin-link">Back</Link>
        </button>
    </div>
}

export default SignIn