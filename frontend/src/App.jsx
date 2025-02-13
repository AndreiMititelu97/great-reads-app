import './App.css'
import HeaderComponent from "./components/HeaderComponent.jsx";
import ViewAllBooksComponent from "./components/book/ViewAllBooksComponent.jsx";
import LoginComponent from "./components/LoginComponent.jsx";
import {Route, Routes} from "react-router-dom";
import FooterComponent from "./components/FooterComponent.jsx";
import RegisterComponent from "./components/RegisterComponent.jsx";
import BookDetailsComponent from "./components/book/BookDetailsComponent.jsx";

function App() {

  return (
    <>
        <HeaderComponent/>
        <Routes>
            <Route path="/" element={<LoginComponent/>}/>
            <Route path="/books" element={<ViewAllBooksComponent/>}/>
            <Route path="/books/:id" element={<BookDetailsComponent/>}/>
            <Route path="/register" element={<RegisterComponent/>}/>
        </Routes>
        <FooterComponent/>
    </>
  )
}

export default App
