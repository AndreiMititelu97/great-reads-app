import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const LogoutComponent = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const logout = () => {
            localStorage.removeItem('token');

            navigate('/login');
            window.location.reload();
        };

        logout();
    }, [navigate]);

    return (
        <>
        </>
    );
};

export default LogoutComponent;
