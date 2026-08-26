import { useState } from "react"
import { Link } from "react-router-dom";
import "../css/LogIn.css"

function LogIn () {

    const [error, setError] = useState("");

    const handleOnSubmit = () => {
        // Enviar datos a la pasarela de verificacion
    }

    return <div className="login">
        <h3 className="login-title">Log In</h3>
        <form onSubmit={handleOnSubmit} className="login-form">
            <input 
                type="text" 
                placeholder="Username"
                className="user-name"
                autoComplete="username"
                required/>
            <input 
                type="password" 
                placeholder="Password"
                className="user-pswd"
                autoComplete="current-password"
                required/>
            <button type="submit" className="login-button">Log in</button>
        </form>
        <button type="submit" className="signin-button">
            <Link to="/sign-in" className="signin-link">Sign In</Link>
        </button>
    </div>
}

export default LogIn