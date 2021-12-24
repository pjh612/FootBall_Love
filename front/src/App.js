import Navbar from "./Navbar";
import LandingPage from "./MainContainer/LandingPage";
import JoinPage from "./AuthComponents/Join/JoinPage";
import LoginPage from "./AuthComponents/Login/LoginPage";
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <div>
        <Navbar></Navbar>
        {/* Routes 내부는 url 에 따라 표현되는 컴포넌트가 달라짐 */}
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/join" element={<JoinPage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
