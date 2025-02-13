const BASE_API = "http://localhost:8080/";

export const getAllBooks = () => fetch(`${BASE_API}books`);