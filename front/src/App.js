import Navbar from "./components/NavBar/Navbar";
import LandingPage from "./components/MainContainer/LandingPage";
import Join from "./components/Join/Join";
import Login from "./components/Login/Login";
import ProfileContainer from "./components/Profile/ProfileContainer";
// import Logout from "./components/Logout/Logout";
// import TeamMakeContainer from "./components/TeamMake/TeamMakeContainer";
// import TeamPage from "./components/TeamPage/TeamPage";
// import WriteMatchContainer from "./components/WriteMatch/WriteContainer";
// import WriteAnyContainer from "./components/WriteAny/WriteAnyContainer";
// import { getUserInfo } from "./axios/axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
// import { useEffect } from "react";
// import {useState} from "react";
//리덕스
// import { combineReducers, createStore } from "redux";
// import { useDispatch } from "react-redux";
// import rootReducer from "./reducer/index";

function App() {
  return (
    <BrowserRouter>
      <div>
        <Navbar></Navbar>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/join" element={<Join />} />
          <Route path="/login" element={<Login />} />
          <Route path="/profile" element={<ProfileContainer />} />
          {/* <Route path="/write" element={<WriteMatchContainer user={user} />} />
          <Route path="/logout" element={<Logout setKey={setKey} />} />
          <Route
            exact
            path="/teammake"
            element={<TeamMakeContainer user={user}></TeamMakeContainer>}
          />
          <Route path="/teampage" element={<TeamPage></TeamPage>} />
          <Route
            path="/writeany"
            element={<WriteAnyContainer user={user}></WriteAnyContainer>} */}
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
