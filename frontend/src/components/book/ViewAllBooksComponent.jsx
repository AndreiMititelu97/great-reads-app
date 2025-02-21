import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Grid, Typography } from "@mui/material";
import { getAllBooks } from "../../api/api.js";
import BookCardComponent from "../book/BookCardComponent.jsx";

const ViewAllBooksComponent = () => {
    const [books, setBooks] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        const response = await getAllBooks();
        const data = await response.json();
        setBooks(data);
    };

    return (
        <Container maxWidth="md">
            <Typography variant="h4" sx={{ my: 3 }}>Books</Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <BookCardComponent book={book} onClick={() => navigate(`/books/${book.id}`)} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}

export default ViewAllBooksComponent;