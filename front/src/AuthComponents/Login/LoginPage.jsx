import styled from 'styled-components';
import SubBar from "./SubBar";
import Login from './Login';

const Div = styled.div`
    width:100vw;
    display:flex;
    flex-direction : column;
    align-items: center;
    overflow: auto;
    overflow-x: hidden;
`;

// flex-direction 을 row 로 해주기 위한 div
const Div2 = styled.div`
    width: 100%;
    height: 100vh;
    
    display:flex;
    flex-direction: row;
    justify-content: center;
`;

const LoginPage = () => {
    return(
        <>
             <Div>
                 <Div2>
                     <Login></Login>
                 </Div2>
            </Div>
        </>
    )
}

export default LoginPage;