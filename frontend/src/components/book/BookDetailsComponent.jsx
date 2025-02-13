import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Container, Typography, Card, CardContent, CardMedia, CircularProgress, List, ListItem, ListItemText } from "@mui/material";

const BASE_API = "http://localhost:8080"; // Adjust if needed

const BookDetailsComponent = () => {
    const { id } = useParams();
    const [book, setBook] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBookDetails = async () => {
            try {
                const bookResponse = await fetch(`${BASE_API}/books/${id}`);
                const reviewResponse = await fetch(`${BASE_API}/reviews/books/${id}`);

                if (!bookResponse.ok || !reviewResponse.ok) throw new Error("Failed to fetch data");

                const bookData = await bookResponse.json();
                const reviewsData = await reviewResponse.json();

                setBook(bookData);
                setReviews(reviewsData);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBookDetails();
    }, [id]);

    if (loading) return <CircularProgress sx={{ display: 'block', margin: 'auto', mt: 5 }} />;
    if (error) return <Typography color="error" sx={{ textAlign: 'center', mt: 5 }}>{error}</Typography>;

    return (
        <Container maxWidth="md">
            {book && (
                <>
                    <Card sx={{ display: "flex", flexDirection: "column", alignItems: "center", padding: 3 }}>
                        <CardMedia
                            component="img"
                            image={book.pageCover}
                            alt={book.title}
                            sx={{ height: 300, width: 200, objectFit: "contain", mb: 2 }}
                        />
                        <CardContent sx={{ textAlign: "center" }}>
                            <Typography variant="h4">{book.title}</Typography>
                            <Typography variant="subtitle1" color="text.secondary">
                                By {book.author.firstName} {book.author.lastName}
                            </Typography>
                            <Typography variant="h6">{book.rating} ⭐ </Typography>
                            <Typography variant="body1" sx={{ mt: 2 }}>{book.description}</Typography>
                            <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                                Genre: {book.genre?.name} | ISBN: {book.isbn} | Number of reviews: {book.numberOfReviews}
                            </Typography>
                        </CardContent>
                    </Card>

                    <Typography variant="h5" sx={{ mt: 4, mb: 2 }}>Reviews</Typography>
                    {reviews.length > 0 ? (
                        <List>
                            {reviews.map((review) => (
                                <ListItem key={review.id} sx={{ borderBottom: "1px solid #ddd" }}>
                                    <ListItemText
                                        primary={`Rating: ${review.rating}/5`}
                                        secondary={`${review.comment} - Published on: ${review.publishedDate}`}
                                    />
                                </ListItem>
                            ))}
                        </List>
                    ) : (
                        <Typography>No reviews yet.</Typography>
                    )}
                </>
            )}
        </Container>
    );
}

export default BookDetailsComponent;