const BASE_API = "http://localhost:8080/";

export const getAllBooks = () => fetch(`${BASE_API}books`);

export const loginUser = async (email, password) => {
    try {
        const response = await fetch(`${BASE_API}user/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            throw new Error("Invalid username or password");
        }

        return await response.text(); // Returns JWT token
    } catch (error) {
        throw error;
    }
};

export const getBookDetails = async (bookId) => {
    try {
        const response = await fetch(`${BASE_API}books/${bookId}`);
        if (!response.ok) throw new Error("Failed to fetch book details");
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const getReviewsForBook = async (bookId) => {
    try {
        const response = await fetch(`${BASE_API}reviews/books/${bookId}`);
        if (!response.ok) throw new Error("Failed to fetch reviews");
        return await response.json();
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const addToWishlist = async (userId, bookId, token) => {
    try {
        const response = await fetch(`${BASE_API}reader/${userId}/books/${bookId}/wishlist`, {
            method: "POST",
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error("Failed to add book to wishlist");
        return response;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const markAsRead = async (userId, bookId, token) => {
    try {
        const response = await fetch(`${BASE_API}reader/${userId}/books/${bookId}/read`, {
            method: "PUT",
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) throw new Error("Failed to mark book as read");
        return response;
    } catch (error) {
        console.error(error);
        throw error;
    }
};

export const getReadBooks = async (userId) => {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch(`${BASE_API}reader/${userId}/read`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) throw new Error("Failed to fetch read books");

        return await response.json();
    } catch (error) {
        console.error("Error fetching books:", error);
        throw error;
    }
};

export const getUserIdFromToken = () => {
    const token = localStorage.getItem("token");

    if (!token) {
        console.error("Token is missing from local storage.");
        return null;
    }

    try {
        const base64Payload = token.split(".")[1];
        if (base64Payload) {
            const payload = JSON.parse(atob(base64Payload));
            return payload.userId; // Extract userId from payload
        } else {
            console.error("Token payload is missing.");
            return null;
        }
    } catch (error) {
        console.error("Error decoding token:", error);
        return null;
    }
};

export const getWishlist = async (userId) => {
    if (!userId) return [];

    try {
        const token = localStorage.getItem("token");
        const response = await fetch(`${BASE_API}reader/${userId}/wishlist`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            throw new Error("Failed to fetch wishlist");
        }

        return await response.json();
    } catch (error) {
        console.error("Error fetching wishlist:", error);
        return [];
    }
};

export const registerUser = async (userData) => {
    try {
        const response = await fetch(`${BASE_API}user/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            throw new Error("Failed to register user");
        }

        return await response.json();
    } catch (error) {
        console.error("Registration error:", error);
        throw error;
    }
};


