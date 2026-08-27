const BASE_URL = "http://localhost:8080";

export const getProducts = async () => {
    const response = await fetch(`${BASE_URL}/product`);
    if(!response.ok) throw new Error("Failed to obtain products");
    const data = await response.json();
    return data.content;
};

export const searchProducts = async (query) => {
    const response = await fetch(`${BASE_URL}/product?query=${encodeURIComponent(query)}`);
    const data = await response.json();
    return data.results;
};