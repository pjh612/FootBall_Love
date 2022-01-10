import Navbar from "./components/NavBar/Navbar";
import LandingPage from "./components/MainContainer/LandingPage";
import JoinPage from "./components/Join/JoinPage";
import Login from "./components/Login/Login";
import ProfileContainer from "./components/Profile/ProfileContainer";
import WriteContainer from "./components/Write/WriteContainer";
import Team from "./components/Team/Team";
import Logout from "./components/Logout/Logout";
import TeamMake from "./components/Team/TeamMake";
import { getUserInfo } from "./axios/axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useState, useEffect } from "react";

function App() {
  const devENV = false;
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
          alert("유저정보를 받아오는데 실패했습니다.");
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
          <Route path="/profile" element={<ProfileContainer user={user} />} />
          <Route path="/write" element={<WriteContainer user={user} />} />
          <Route path="/team" element={<Team />} />
          <Route path="/logout" element={<Logout setKey={setKey} />} />
          <Route path="/teammake" element={<TeamMake></TeamMake>} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
