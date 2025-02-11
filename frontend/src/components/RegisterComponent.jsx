import { useState } from "react";
import { TextField, MenuItem, Button, FormControl, InputLabel, Select, Container, Typography, Box, Avatar } from '@mui/material';

const roles = ["READER", "AUTHOR", "ADMIN"];

const RegisterComponent = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('READER');

    const handleEmailChange = (event) => setEmail(event.target.value);
    const handlePasswordChange = (event) => setPassword(event.target.value);
    const handleRoleChange = (event) => setRole(event.target.value);

    const handleSubmit = (event) => {
        event.preventDefault();
        const user = { email, password, role };
        console.log('User data to register:', user);
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{ mt: 5, p: 3, boxShadow: 3, borderRadius: 2, display: 'flex', flexDirection: 'column',
                alignItems: 'center' }}>
                <Avatar
                    alt="Logo Image"
                    src="../../public/logo.jpeg"
                    sx={{ width: 150, height: 150, mb: 3 }}
                />
                <Typography variant="h4" gutterBottom>Register</Typography>
                <form onSubmit={handleSubmit} style={{ width: '100%' }}>
                    <TextField
                        label="Email"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        value={email}
                        onChange={handleEmailChange}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        required
                        margin="normal"
                        value={password}
                        onChange={handlePasswordChange}
                    />

                    <FormControl fullWidth required margin="normal">
                        <InputLabel>Role</InputLabel>
                        <Select
                            label="Role"
                            value={role}
                            onChange={handleRoleChange}
                            variant="outlined"
                        >
                            {roles.map((roleOption) => (
                                <MenuItem key={roleOption} value={roleOption}>
                                    {roleOption}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <Button
                        type="submit"
                        variant="contained"
                        fullWidth
                        sx={{ mt: 2 }}
                    >
                        Register
                    </Button>
                </form>
            </Box>
        </Container>
    );
};

export default RegisterComponent;