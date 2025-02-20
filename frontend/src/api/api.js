const BASE_API = "http://localhost:8080/";

export const getAllBooks = () => fetch(`${BASE_API}books`);
export const getBookDetails = (id) => fetch(`${BASE_API}books/${id}`);

export const getReviewsForBook = (id) => fetch(`${BASE_API}reviews/books/${id}`);