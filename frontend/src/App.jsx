import './App.css'
import HeaderComponent from "./components/HeaderComponent.jsx";
import BooksComponent from "./components/BooksComponent.jsx";
import LoginComponent from "./components/LoginComponent.jsx";
import {Route, Routes} from "react-router-dom";
import FooterComponent from "./components/FooterComponent.jsx";
import RegisterComponent from "./components/RegisterComponent.jsx";

function App() {

  return (
    <>
        <HeaderComponent/>
        <Routes>
            <Route path="/" element={<LoginComponent/>}/>
            <Route path="/books" element={<BooksComponent/>}/>
            <Route path="/register" element={<RegisterComponent/>}/>
        </Routes>
        <FooterComponent/>
    </>
  )
}

export default App
