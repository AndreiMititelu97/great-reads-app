import { useEffect, useState } from "react";
import { Container, Typography, Grid } from "@mui/material";
import BookCardComponent from "../book/BookCardComponent.jsx";
import { getReadBooks } from "../../api/api.js";
import {useNavigate} from "react-router-dom";

const ReadBooksComponent = () => {
    const [books, setBooks] = useState([]);
    const [userId, setUserId] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (token) {
            try {
                const base64Payload = token.split(".")[1];
                if (base64Payload) {
                    const payload = JSON.parse(atob(base64Payload));
                    setUserId(payload.userId);
                } else {
                    console.error("Token payload is missing.");
                }
            } catch (error) {
                console.error("Error decoding token:", error);
            }
        } else {
            console.error("Token is missing from local storage.");
        }
    }, []);

    useEffect(() => {
        if (userId) {
            getReadBooks(userId)
                .then((data) => setBooks(data))
                .catch((err) => console.error(err));
        }
    }, [userId]);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Books I've Read
            </Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <BookCardComponent book={book} onClick={() => navigate(`/books/${book.id}`)}/>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default ReadBooksComponent;