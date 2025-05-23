import { AppBar, Toolbar, Typography, Button, Avatar } from "@mui/material";
import { Link } from "react-router-dom";

const HeaderComponent = () => {
    const token = localStorage.getItem('token');

    return (
        <AppBar position="static">
            <Toolbar>
                <Avatar
                    alt="Logo Image"
                    src="../../public/logo.jpeg"
                    sx={{ width: 50, height: 50, mr: 1 }}
                />
                <Typography variant="h6" sx={{ flexGrow: 1 }}>Great Reads</Typography>
                {token && <Button color="inherit" component={Link} to="/reader">My Books</Button>}
                <Button color="inherit" component={Link} to="/books">Books</Button>
                |
                <Button color="inherit" component={Link} to="/">Login</Button>
                <Button color="inherit" component={Link} to="/register">Register</Button>
                {token && <Button color="inherit" component={Link} to="/logout">Logout</Button>}
            </Toolbar>
        </AppBar>
    );
}

export default HeaderComponent;