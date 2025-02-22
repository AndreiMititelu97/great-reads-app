import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {TextField, Button, Container, Typography, Box, Avatar,} from "@mui/material";
import { registerUser } from "../../api/api.js";

const RegisterComponent = () => {
    const [formData, setFormData] = useState({
        email: "",
        password: "",
        firstName: "",
        lastName: ""
    });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleChange = (event) => {
        setFormData({
            ...formData,
            [event.target.name]: event.target.value,
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            await registerUser(formData);
            console.log("User registered successfully:", formData);
            navigate("/");
        } catch (error) {
            setError("Registration failed. Please try again.");
        }
    };

    return (
        <Container maxWidth="sm">
            <Box
                sx={{
                    mt: 5,
                    p: 3,
                    boxShadow: 3,
                    borderRadius: 2,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                <Avatar
                    alt="Logo"
                    src="/logo.jpeg"
                    sx={{ width: 100, height: 100, mb: 3 }}
                />
                <Typography variant="h4" gutterBottom>
                    Register
                </Typography>
                {error && <Typography color="error">{error}</Typography>}
                <form onSubmit={handleSubmit} style={{ width: "100%" }}>
                    <TextField
                        label="First Name"
                        name="firstName"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        value={formData.firstName}
                        onChange={handleChange}
                    />
                    <TextField
                        label="Last Name"
                        name="lastName"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        value={formData.lastName}
                        onChange={handleChange}
                    />
                    <TextField
                        label="Email"
                        name="email"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        type="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                    <TextField
                        label="Password"
                        name="password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        value={formData.password}
                        onChange={handleChange}
                    />
                    <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                        Register
                    </Button>
                </form>
            </Box>
        </Container>
    );
};

export default RegisterComponent;