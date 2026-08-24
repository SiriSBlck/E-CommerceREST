import { Link } from "react-router-dom"
import "../css/NavBar.css"

function NavBar() {
    return <nav className="navbar">
        <div className="navbar-links">
            <Link to='/'><svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#e3e3e3"><path d="M240-200h120v-240h240v240h120v-360L480-740 240-560v360Zm-80 80v-480l320-240 320 240v480H520v-240h-80v240H160Zm320-350Z"/></svg></Link>
            <Link to='/{client-id}/cart' className="nav-link">Cart</Link>
            <Link to='/{supplier-id}/catalog' className="nav-link"></Link>
            <Link to='/product' className="nav-link">Products</Link>
            <Link to='/sign-in' className="nav-link">Sign In</Link>
            <Link to='/{user-id}' className="nav-link">My Page</Link>
            <Link to='/log-in' className="nav-link">Log In</Link>
        </div>
    </nav>
}

export default NavBar