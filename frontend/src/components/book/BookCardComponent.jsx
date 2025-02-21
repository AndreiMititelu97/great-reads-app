import React from 'react';
import { Card, CardContent, CardMedia, Typography, Box } from "@mui/material";

const BookCardComponent = ({ book, onClick }) => {
    return (
        <Card
            sx={{ maxWidth: 220, margin: 'auto', height: 380, cursor: 'pointer', backgroundColor: '#f5f5f5' }}
            onClick={onClick}
        >
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <Typography variant="overline" color="text.primary" sx={{ mt: 1, textAlign: 'center' }}>
                    {book.genre?.name}
                </Typography>
                <CardMedia
                    component="img"
                    image={book.pageCover}
                    alt={book.title}
                    sx={{ objectFit: 'contain', height: 250, width: '100%' }}
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
                <Typography variant="body2" color="text.secondary" sx={{ textAlign: 'center' }}>
                    {book.author.firstName} {book.author.lastName}
                </Typography>
            </CardContent>
        </Card>
    );
};

export default BookCardComponent;