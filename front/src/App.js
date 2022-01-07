import Navbar from "./components/NavBar/Navbar";
import LandingPage from "./components/MainContainer/LandingPage";
import JoinPage from "./components/Join/JoinPage";
import Login from "./components/Login/Login";
import Profile from "./components/Profile/Profile";
import { getUserInfo } from "./axios/axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useState, useEffect } from "react";
import { getLocalStorage } from "./setLocalStorage";
function App() {
  const [user, setUser] = useState(null);
  const [key, setKey] = useState(null);

  useEffect(() => {
    const key = getLocalStorage();
    if (key) {
      setKey(key);
    }
  }, []);

  useEffect(() => {
    if (key) {
      getUserInfo(key)
        .then((res) => {
          setUser(res.data.user);
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }, [key]);

  return (
    <BrowserRouter>
      <div>
        <Navbar user={user}></Navbar>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/join" element={<JoinPage />} />
          <Route path="/login" element={<Login setKey={setKey} />} />
          <Route path="/profile" element={<Profile user={user} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
