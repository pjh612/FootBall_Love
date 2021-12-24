import styled from 'styled-components';
import {useState} from 'react';

const Div = styled.div`
    margin-top: 100px;
    width: 50%;
    border: 1px solid red;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
`;

const IdLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const IdInput = styled.input`
    width: 60%;
    height: 50px;
    margin-left: 5%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;

const PwdLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    margin-top: 50px;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const PwdInput = styled.input`
    width: 60%;
    height: 50px;
    margin-left: 5%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;


const IDInputBox = ({loginInput, setLoginInput}) => {
    const [idFocus, setIdFocus] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    return(
    <>
        <Div>
        <IdInput onFocus={() => setIdFocus(true)}
                          onBlur={() => loginInput.id ? null : setIdFocus(false)}
                          onChange={(e) => 
                            setLoginInput({...loginInput, 
                                id : e.target.value})
                            }
                           id="id"
                           ></IdInput>
                <IdLabel focus={idFocus} htmlFor="id">아이디</IdLabel>
                <PwdInput onFocus={() => setPwdFocus(true)}
                          onBlur={() => loginInput.pwd ? null : setPwdFocus(false)}
                          onChange={(e) => 
                            setLoginInput({...loginInput, 
                                pwd : e.target.value})
                            }
                           type="password"
                           id="pwd"></PwdInput>
                <PwdLabel focus={pwdFocus} htmlFor="pwd">비밀번호</PwdLabel>
                </Div>
        
    </>)
}

export default IDInputBox;