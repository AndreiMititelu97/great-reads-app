import { useEffect, useState } from "react";
import { Container, Typography, Grid } from "@mui/material";
import BookCardComponent from "../book/BookCardComponent.jsx";
import { getUserIdFromToken, getWishlist } from "../../api/api.js";
import {useNavigate} from "react-router-dom";

const WishlistComponent = () => {
    const [books, setBooks] = useState([]);
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const id = getUserIdFromToken();
        setUserId(id);
    }, []);

    useEffect(() => {
        const fetchWishlist = async () => {
            if (!userId) return;
            const wishlist = await getWishlist(userId);
            setBooks(wishlist);
        };

        fetchWishlist();
    }, [userId]);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                My Wishlist
            </Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <BookCardComponent book={book} onClick={() => navigate(`/books/${book.id}`)} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default WishlistComponent;