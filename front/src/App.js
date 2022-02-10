import NavBar from "./components/NavBar/NavBar";
import LandingPage from "./components/LandingPage/LandingPage";
import Join from "./components/Join/Join";
import Login from "./components/Login/Login";
import Profile from "./components/Profile/Profile";
import Logout from "./components/Logout/Logout";
import TeamMake from "./components/TeamMake/TeamMake";
import TeamPage from "./components/TeamPage/TeamPage";
import Board from "./components/Board/Board";
import Write from "./components/Board/WritePage";
import MyPage from "./components/MyPage/MyPage";
import TeamList from "./components/TeamListPage/TeamList";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { updateUserAction, updateTeamAction } from "./action/createAction";

// import WriteMatchContainer from "./components/WriteMatch/WriteContainer";
// import WriteAnyContainer from "./components/WriteAny/WriteAnyContainer";
import { getUserInfo, getTeamInfo } from "./axios/axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
// import { useEffect } from "react";
// import {useState} from "react";
//리덕스
// import { combineReducers, createStore } from "redux";
// import { useDispatch } from "react-redux";
// import rootReducer from "./reducer/index";

function App() {
  const dispatch = useDispatch();
  const theme = createTheme({
    palette: {
      dark: {
        main: "#1F1E1E",
      },
    },
  });

  useEffect(() => {
    async function refresh() {
      try {
        const userInfo = await getUserInfo().then((res) => res.data);
        const teamInfo = await getTeamInfo().then((res) => res.data);
        const useraction = updateUserAction(userInfo);
        const teamaction = updateTeamAction(teamInfo);
        dispatch(useraction);
        dispatch(teamaction);
      } catch (err) {
        console.log("로그인에러 [쿠키의 값이 유효하지 않습니다]");
        console.log(err.response);
        console.log(err);
      }
    }
    refresh();
  }, []);

  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <div>
          <NavBar></NavBar>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/join" element={<Join />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            {/* <Route path="/write" element={<WriteMatchContainer user={user} />} /> */}
            <Route path="/logout" element={<Logout />} />
            <Route exact path="/teammake" element={<TeamMake></TeamMake>} />
            <Route path="/teampage" element={<TeamPage></TeamPage>} />
            <Route path="/board" element={<Board></Board>} />
            <Route path="/myPage" element={<MyPage />} />
            <Route path="/teamlist" element={<TeamList />} />
            <Route path="/board/write" element={<Write></Write>}></Route>
            {/* <Route
            path="/writeany"
            element={<WriteAnyContainer user={user}></WriteAnyContainer>}
          /> */}
          </Routes>
        </div>
      </ThemeProvider>
    </BrowserRouter>
  );
}

export default App;
