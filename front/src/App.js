import Navbar from "./Navbar";
import LandingPage from "./MainContainer/LandingPage";
import JoinPage from "./AuthComponents/Join/JoinPage";
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  // const [resize, setResize] = useState(false);
  // const resizeHandler = () => {
  //   setResize(!resize);
  // };
  // useEffect(() => {
  //   window.addEventListener("resize", resizeHandler);
  //   return () => {
  //     // 메모리 누수를 줄이기 위한 removeEvent
  //     window.removeEventListener("resize", resizeHandler);
  //   };
  // }, []);
  return (
    <BrowserRouter>
      <div>
        <Navbar></Navbar>
        {/* Routes 내부는 url 에 따라 표현되는 컴포넌트가 달라짐 */}
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/join" element={<JoinPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
