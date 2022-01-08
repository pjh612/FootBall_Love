import Navbar from "./components/NavBar/Navbar";
import LandingPage from "./components/MainContainer/LandingPage";
import JoinPage from "./components/Join/JoinPage";
import Login from "./components/Login/Login";
import Profile from "./components/Profile/Profile";
import Team from "./components/Team/Team";
import Logout from "./components/Logout/Logout";
import { getUserInfo } from "./axios/axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useState, useEffect } from "react";

function App() {
  const devENV = true;
  const [user, setUser] = useState(devENV);
  const [key, setKey] = useState(devENV);

  useEffect(() => {
    if (key) {
      getUserInfo(key)
        .then((res) => {
          setUser(res.data[0]);
        })
        .catch((err) => {
          console.error(err);
        });
    } else {
      setUser(null);
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
          <Route path="/team" element={<Team />} />
          <Route path="/logout" element={<Logout setKey={setKey} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
