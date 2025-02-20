import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Grid, Card, CardContent, Typography, CardMedia, Box } from "@mui/material";
import { getAllBooks } from "../../api/api.js";

export default function ViewAllBooksComponent() {
    const [books, setBooks] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchBooks()
    }, []);

    const fetchBooks = async () => {
        const response = await getAllBooks();
        const data = await response.json();
        setBooks(data);
    }

    return (
        <Container maxWidth="md">
            <Typography variant="h4" sx={{ my: 3 }}>Books</Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <BookCard book={book} onClick={() => navigate(`/books/${book.id}`)} />
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}

function BookCard({ book, onClick }) {
    return (
        <Card
            sx={{maxWidth: 220, margin: 'auto', height: 380, cursor: 'pointer', backgroundColor: '#f5f5f5'}}
            onClick={onClick}
        >
            <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
                <Typography variant="overline" color="text.primary" sx={{mt: 1, textAlign: 'center'}}>
                    {book.genre?.name}
                </Typography>
                <CardMedia
                    component="img"
                    image={book.pageCover}
                    alt={book.title}
                    sx={{objectFit: 'contain', height: 250, width: '100%'}}
                />
            </Box>
            <CardContent sx={{
                flexGrow: 1,
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <Typography variant="h6" sx={{
                    fontSize: '1rem',
                    textAlign: 'center',
                    wordWrap: 'break-word',
                    maxWidth: '100%',
                    overflowWrap: 'break-word'
                }}>
                    {book.title}
                </Typography>
                <Typography variant="body2" color="text.secondary" sx={{textAlign: 'center'}}>
                    {book.author.firstName} {book.author.lastName}
                </Typography>
            </CardContent>
        </Card>
    );
}