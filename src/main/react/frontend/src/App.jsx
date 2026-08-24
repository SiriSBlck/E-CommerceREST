import './css/App.css'
import Home from './pages/Home'
import SignIn from './pages/SignIn'
import LogIn from './pages/LogIn'
import Cart from './pages/Cart'
import ProductPage from './pages/ProductPage'
import PersonalPage from './pages/PersonalPage'
import SupplierCatalog from './pages/SupplierCatalog'
import ProductSelection from './pages/ProductSelection'
import {Routes, Route} from "react-router-dom"
import NavBar from './components/NavBar'

function App() {

  return (
    <>
      <div className="navBar">
        <NavBar />
      </div>
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="/sign-in" element={<SignIn />}/>
          <Route path="/log-in" element={<LogIn />}/>
          <Route path="/{client-id}/cart" element={<Cart />}/>
          <Route path="/{user-id}" element={<PersonalPage />}/>
          <Route path="/{supplier-id}/catalog" element={<SupplierCatalog />}/>
          <Route path="/product" element={<ProductSelection />}/>
          <Route path="/product/{product-id}" element={<ProductPage />}/>
        </Routes>
      </main>
    </>
  );
}

export default App
