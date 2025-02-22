import { Container, Typography, Card, CardContent, Grid, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const ReaderDashboard = () => {
    const navigate = useNavigate();

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                My Books
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <Card>
                        <CardContent>
                            <Typography variant="h6">Wishlist</Typography>
                            <Typography variant="body2">View books you want to read.</Typography>
                            <Button variant="contained" color="primary" onClick={() => navigate("/reader/wishlist")}>
                                Go to Wishlist
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} md={6}>
                    <Card>
                        <CardContent>
                            <Typography variant="h6">Read Books</Typography>
                            <Typography variant="body2">See books you've marked as read.</Typography>
                            <Button variant="contained" color="primary" onClick={() => navigate("/reader/read")}>
                                View Read Books
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </Container>
    );
};

export default ReaderDashboard;
