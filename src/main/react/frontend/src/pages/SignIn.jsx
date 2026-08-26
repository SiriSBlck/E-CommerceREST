import { useState } from "react"
import { Link } from "react-router-dom";
import "../css/SignIn.css"

function SignIn () {

    const [error, setError] = useState("");

    const handleOnSubmit = () => {
        // Enviar datos a la pasarela de verificacion
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
                    autoComplete="username"
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Displayed name</p>
                <input 
                    type="text" 
                    placeholder="Username"
                    className="user-name"
                    autoComplete="username"
                    required/>
            </div>      
            <div className="elem">
                <p className="field-text">Password</p>
                <input 
                    type="password" 
                    placeholder="Password"
                    className="user-pswd"
                    autoComplete="current-password"
                    required/>
            </div>
            <div className="elem">
                <p className="field-text">Confirm password</p>
                <input 
                    type="password" 
                    placeholder="Password"
                    className="user-pswd"
                    autoComplete="current-password"
                    required/>
            </div>
            <button type="submit" className="signin-button">Sign in</button>
        </form>
        <button type="submit" className="signin-button">
            <Link to="/log-in" className="signin-link">Back</Link>
        </button>
    </div>
}

export default SignIn