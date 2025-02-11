import {Box, Typography, Container} from "@mui/material";
import GitHubIcon from '@mui/icons-material/GitHub';
import LinkedInIcon from '@mui/icons-material/LinkedIn';

const FooterComponent = () => {
    const gitHubProfile = "https://github.com/AndreiMititelu97";
    const linkedInProfile = "https://www.linkedin.com/in/andrei-calin-mititelu/";

    return (
        <Box
            component="footer"
            sx={{
                py: 1,
                mt: 5,
                backgroundColor: "primary.main",
                color: "white",
                textAlign: "center",
                position: "fixed",
                left: "0",
                bottom: "0",
                width: "100%",
            }}
        >
            <Container>
                <Typography variant="body1">© 2025 Great Reads</Typography>
                <GitHubIcon
                    onClick={() => window.open(gitHubProfile, "_blank")}
                    sx={{ cursor: 'pointer', marginRight: 1 }}
                />
                <LinkedInIcon
                    onClick={() => window.open(linkedInProfile, "_blank")}
                    sx={{ cursor: 'pointer' }}
                />
            </Container>
        </Box>
    );
}
export default FooterComponent;