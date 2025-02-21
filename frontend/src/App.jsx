import './App.css'
import HeaderComponent from "./components/HeaderComponent.jsx";
import ViewAllBooksComponent from "./components/book/ViewAllBooksComponent.jsx";
import LoginComponent from "./components/auth/LoginComponent.jsx";
import {Route, Routes} from "react-router-dom";
import FooterComponent from "./components/FooterComponent.jsx";
import RegisterComponent from "./components/auth/RegisterComponent.jsx";
import BookDetailsComponent from "./components/book/BookDetailsComponent.jsx";
import ReaderDashboardComponent from "./components/reader/ReaderDashboardComponent.jsx";
import WishlistComponent from "./components/reader/WishlistComponent.jsx";
import ReadBooksComponent from "./components/reader/ReadBooksComponent.jsx";
import LogoutComponent from "./components/auth/LogoutComponent.jsx";

function App() {

  return (
    <>
        <HeaderComponent/>
        <Routes>
            <Route path="/login" element={<LoginComponent/>}/>
            <Route path="/books" element={<ViewAllBooksComponent/>}/>
            <Route path="/books/:id" element={<BookDetailsComponent/>}/>
            <Route path="/register" element={<RegisterComponent/>}/>
            <Route path="/logout" element={<LogoutComponent/>}/>

            <Route path="/reader" element={<ReaderDashboardComponent/>}/>
            <Route path="/reader/wishlist" element={<WishlistComponent/>}/>
            <Route path="/reader/read" element={<ReadBooksComponent/>}/>
        </Routes>
        <FooterComponent/>
    </>
  )
}

export default App
