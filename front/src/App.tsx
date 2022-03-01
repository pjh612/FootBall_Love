import NavBar from './components/NavBar/NavBar';
import LandingPage from './components/LandingPage/LandingPage';
import Join from './components/Join/Join';
import Login from './components/Login/Login';
import Profile from './components/Profile/Profile';
import Logout from './components/Logout/Logout';
import TeamMake from './components/TeamMake/TeamMake';
import TeamPage from './components/TeamPage/TeamPage';
import Board from './components/Board/Board';
import Write from './components/Board/WritePage';
import MyPage from './components/MyPage/MyPage';
import TeamList from './components/TeamListPage/TeamList';
import BoardPage from './components/TeamPage/TeamBoardPage';
import AdminPage from './components/Admin/AdminPage';
import Community from './components/community/Community';
import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { updateUserAction, updateTeamAction } from './action/createAction';
import { useUser } from './hooks/useUser';
import { getUserInfo, getTeamInfo } from './axios/axios';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import TeamBoardPage from './components/TeamPage/TeamBoardPage';

function App() {
  const dispatch = useDispatch();
  const theme = createTheme({
    palette: {
      dark: {
        main: '#1F1E1E',
      },
    },
  });
  const user = useUser();

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
        console.log('로그인에러 [쿠키의 값이 유효하지 않습니다]');
        console.log(err.response);
        console.log(err);
      }
    }
    refresh();
  }, []);

  return (
    <div>
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <NavBar></NavBar>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/join" element={<Join />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/logout" element={<Logout />} />
            <Route path="/teammake" element={<TeamMake></TeamMake>} />
            <Route path="/board" element={<Board></Board>} />
            <Route path="/myPage" element={<MyPage />} />
            <Route path="/teamlist" element={<TeamList />} />
            <Route path="/teams/:id/:boardNumber/write" element={<Write></Write>}></Route>
            <Route path="/teams/:id" element={<TeamPage></TeamPage>}></Route>
            <Route path="/teams/:id/:boardNumber" element={<TeamBoardPage></TeamBoardPage>}></Route>
            <Route path="/community" element={<Community></Community>}></Route>
            <Route path="/admin" element={<AdminPage user={user}></AdminPage>}></Route>
          </Routes>
        </ThemeProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
