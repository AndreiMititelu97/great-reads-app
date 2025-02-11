import { Container, Grid, Card, CardContent, Typography, CardMedia, Box } from "@mui/material";

const books = [
    { id: 1, title: "The Hobbit", author: "J.R.R. Tolkien", image: "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546071216i/5907.jpg", genre: "Fantasy" },
    { id: 2, title: "1984", author: "George Orwell", image: "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1657781256i/61439040.jpg", genre: "Dystopian" },
];

export default function BooksComponent() {
    return (
        <Container maxWidth="md">
            <Typography variant="h4" sx={{ my: 3 }}>Books</Typography>
            <Grid container spacing={3}>
                {books.map((book) => (
                    <Grid item xs={12} sm={6} md={4} key={book.id}>
                        <Card sx={{ maxWidth: 220, margin: 'auto' }}>
                            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>{/* Added Box for genre and image */}
                                <Typography variant="overline" color="text.primary" sx={{ mt: 1, textAlign: 'center' }}>  {/* Genre display */}
                                    {book.genre}
                                </Typography>
                                <CardMedia
                                    component="img"
                                    image={book.image}
                                    alt={book.title}
                                    sx={{
                                        objectFit: 'contain',
                                        height: 250,
                                        width: '100%',
                                    }}
                                />
                            </Box> {/* Close the Box */}
                            <CardContent sx={{ paddingBottom: '16px' }}>
                                <Typography variant="h6" sx={{ fontSize: '1rem', textAlign: 'center' }}>{book.title}</Typography>
                                <Typography variant="body2" color="text.secondary" sx={{ textAlign: 'center' }}>
                                    {book.author}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
}