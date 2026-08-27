import ProductCard from "../components/ProductCard"
import {useState, useEffect} from "react";
import "../css/Home.css"
import { getProducts, searchProducts  } from "../services/api";

function Home() {

    const [searchQuery, setSearchQuery] = useState("");
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadProducts = async () => {
            try {
                const popularProducts = await getProducts();
                setProducts(popularProducts);
            } catch (e) {
                console.log(e)
                setError("Failed to load products: ", e);
            } finally {
                setLoading(false);
            }
        };
        loadProducts();
    }, [])

    const handleSearch = (e) => {
        e.preventDefault()
        alert(searchQuery)
        setSearchQuery("")
    }

    // const products = [
    //     {id: 1, name: "POCO", image: "https://storage.comprasmartphone.com/smartphones/poco-x8-pro-max.png", score: 5.0},
    //     {id: 2, name: "Samsung", image: "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcRH0UnMRl1WZRiy9egq3KHLcDYqCg5mpwpZvFDTkZCN2yeB2NSZpgWvY6cS5nxua2aQ5UvZCNCUbUNwQlVwntudTsmAZBS1HrxEtqdeh7j8fSlKdUoiBWEq4FjXzokkJnXGubqm_w&usqp=CAc", score: 3.4},
    //     {id: 3, name: "Apple", image: "https://www.apple.com/euro/iphone/home/d/screens_alt/images/meta/iphone__bh930eyjnj0i_og.png", score: 2.5},
    //     {id: 5, name: "Samsung", image: "", score: 3.4},
    //     {id: 6, name: "Apple", image: "", score: 2.5},
    //     {id: 7, name: "Apple", image: "", score: 2.5},
    // ]

    if (loading) {
        return <p>Cargando productos...</p>;
    }
    if (error) {
        return <p>{error}</p>;
    }
    return <div className="home">
        <form onSubmit={handleSearch} className="search-form">
            <input 
                type="text"
                placeholder="Search for products"
                className="search-input"
                value={searchQuery} 
                onChange={(e) => setSearchQuery(e.target.value)} />
            <button className="search-button">Search</button>
        </form>
         <div className="products-grid">
            {products.map((product) => (
                <ProductCard product={product} key={product.id}/>
            ))}
         </div>
    </div>
}

export default Home