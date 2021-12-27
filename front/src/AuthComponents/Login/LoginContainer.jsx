import styled from 'styled-components';
import SubBar from "./SubBar";
import IDInputBox from "./IDInputBox";
import Deco from './Deco';

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
    border: 1px solid red;
    display:flex;
    flex-direction: row;
    justify-content: center;
`;

const LoginContainer = ({loginInput, setLoginInput}) => {
    return(
        <>
             <Div>
                 <SubBar></SubBar>
                 <Div2>
                     <Deco></Deco>
                    <IDInputBox loginInput={loginInput} setLoginInput={setLoginInput}></IDInputBox>
                 </Div2>
            </Div>
        </>
    )
}

export default LoginContainer;