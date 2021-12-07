import Navbar from "./Navbar";
import LandingPage from './MainContainer/LandingPage';
import JoinPage from './AuthComponents/JoinPage';
import {BrowserRouter, Route, Routes} from 'react-router-dom'

function App() {
  return (
    <BrowserRouter>
    <div>
      <Navbar></Navbar>
      {/* Routes 내부는 url 에 따라 표현되는 컴포넌트가 달라짐 */}
      <Routes>
        <Route path="/" element={<LandingPage/>} />
        <Route path="/join" element={<JoinPage/>}/>
      </Routes>
    </div>
    </BrowserRouter>
  );
}

export default App;
