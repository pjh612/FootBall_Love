import LoginContainer from "./LoginContainer";
import {useState} from "react";

const LoginPage = () => {
    const [loginInput, setLoginInput] = useState({
        id : null,
        pwd : null,
        type : 'NORMAL',
    })
    return(
        <>
            <LoginContainer loginInput={loginInput} setLoginInput={setLoginInput}></LoginContainer>
        </>
    )
}

export default LoginPage;