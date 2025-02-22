import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Container, Typography, Card, CardContent, CardMedia, List, ListItem, ListItemText, Button, Stack, TextField, Rating } from "@mui/material";
import { getBookDetails, getReviewsForBook, addToWishlist, markAsRead, addReview } from "../../api/api.js";

const BookDetailsComponent = () => {
    const { id } = useParams();
    const [book, setBook] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [userId, setUserId] = useState(null);
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (token) {
            try {
                const payload = JSON.parse(atob(token.split(".")[1]));
                setUserId(payload.userId);
            } catch (error) {
                console.error("Error decoding token:", error);
            }
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const bookData = await getBookDetails(id);
                const reviewsData = await getReviewsForBook(id);
                setBook(bookData);
                setReviews(reviewsData);
            } catch (error) {
                console.error("Error fetching book details:", error);
            }
        };

        fetchData();
    }, [id]);

    const handleAddToWishlist = async () => {
        if (!userId) return;

        const token = localStorage.getItem("token");

        try {
            await addToWishlist(userId, id, token);
            alert("Book added to wishlist!");
        } catch (error) {
            alert(error.message);
        }
    };

    const handleMarkAsRead = async () => {
        if (!userId) return;

        const token = localStorage.getItem("token");

        try {
            await markAsRead(userId, id, token);
            alert("Book marked as read!");
        } catch (error) {
            alert(error.message);
        }
    };

    const handleReviewSubmit = async (e) => {
        e.preventDefault();
        if (!userId) {
            alert("You must be logged in to leave a review.");
            return;
        }

        const token = localStorage.getItem("token");

        try {
            await addReview({ userId, bookId: id, rating, comment }, token);
            alert("Review submitted successfully!");
            setRating(0);
            setComment("");

            // Refresh the reviews and book data after submission
            const updatedReviews = await getReviewsForBook(id);
            setReviews(updatedReviews);

            // Recalculate the average rating of the book
            const updatedBookData = await getBookDetails(id);
            setBook(updatedBookData);
        } catch (error) {
            alert("Error submitting review: " + error.message);
        }
    };

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

                            <Stack direction="row" spacing={2} sx={{ mt: 3 }}>
                                <Button variant="contained" color="secondary" onClick={handleAddToWishlist}>
                                    Add to Wishlist
                                </Button>
                                <Button variant="contained" color="primary" onClick={handleMarkAsRead}>
                                    Mark as Read
                                </Button>
                            </Stack>
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

                    {userId && (
                        <Card sx={{ mt: 4, p: 2 }}>
                            <Typography variant="h6">Leave a Review</Typography>
                            <form onSubmit={handleReviewSubmit}>
                                <Rating
                                    value={rating}
                                    onChange={(e, newValue) => setRating(newValue)}
                                    size="large"
                                    sx={{ mt: 2 }}
                                />
                                <TextField
                                    label="Comment"
                                    fullWidth
                                    multiline
                                    rows={3}
                                    value={comment}
                                    onChange={(e) => setComment(e.target.value)}
                                    sx={{ mt: 2 }}
                                />
                                <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                                    Submit Review
                                </Button>
                            </form>
                        </Card>
                    )}
                </>
            )}
        </Container>
    );
};

export default BookDetailsComponent;
