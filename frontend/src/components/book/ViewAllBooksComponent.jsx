import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Grid, Card, CardContent, Typography, CardMedia, Box, CircularProgress } from "@mui/material";
import { getAllBooks } from "../../api/booksApi.js";

export default function ViewAllBooksComponent() {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        getAllBooks()
            .then((response) => response.json())
            .then((data) => {
                setBooks(data);
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <CircularProgress sx={{ display: 'block', margin: 'auto', mt: 5 }} />;
    if (error) return <Typography color="error" sx={{ textAlign: 'center', mt: 5 }}>{error}</Typography>;

    return (
        <Container maxWidth="md">
            <Typography variant="h4" sx={{ my: 3 }}>Books</Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <Card
                            sx={{ maxWidth: 220, margin: 'auto', height: 380, cursor: 'pointer', backgroundColor: '#f5f5f5' }}
                            onClick={() => navigate(`/books/${book.id}`)}
                        >
                            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                                <Typography variant="overline" color="text.primary" sx={{ mt: 1, textAlign: 'center' }}>
                                    {book.genre?.name}
                                </Typography>
                                <CardMedia
                                    component="img"
                                    image={book.pageCover}
                                    alt={book.title}
                                    sx={{
                                        objectFit: 'contain',
                                        height: 250,
                                        width: '100%',
                                    }}
                                />
                            </Box>
                            <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>
                                <Typography variant="h6" sx={{ fontSize: '1rem', textAlign: 'center', wordWrap: 'break-word', maxWidth: '100%', overflowWrap: 'break-word' }}>
                                    {book.title}
                                </Typography>
                                <Typography variant="body2" color="text.secondary" sx={{ textAlign: 'center' }}>
                                    {book.author.firstName} {book.author.lastName}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}