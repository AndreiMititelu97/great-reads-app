import { useState } from "react";
import { TextField, Button, Container, Typography, Box, Avatar } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../api/api.js";

export default function LoginComponent() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            const token = await loginUser(email, password);
            localStorage.setItem("token", token);
            navigate("/reader");
            window.location.reload();
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ mt: 5, p: 3, boxShadow: 3, borderRadius: 2, display: "flex", flexDirection: "column", alignItems: "center" }}>
                <Avatar alt="Logo Image" src="../../public/logo.jpeg" sx={{ width: 150, height: 150, mb: 3 }} />
                <Typography variant="h4" gutterBottom>Login</Typography>
                {error && <Typography color="error" sx={{ mb: 2 }}>{error}</Typography>}
                <form onSubmit={handleSubmit} style={{ width: "100%" }}>
                    <TextField
                        label="Email"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <TextField
                        label="Password"
                        type="password"
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                        Login
                    </Button>
                </form>
            </Box>
        </Container>
    );
}