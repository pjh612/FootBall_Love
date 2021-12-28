import styled from 'styled-components';
import {useState} from 'react';

// Id Pwd NickName

const Div = styled.div`
    margin-top:5px;
    width: 35vw;
    height: 10vh;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    
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
    width: 90%;
    height: 64%;
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
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const PwdInput = styled.input`
    width: 90%;
    height: 64%;
    margin-left: 5%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;

const NicknameLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const NicknameInput = styled.input`
    width: 90%;
    height: 64%;
    margin-left: 5%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;



const Div2items = ({newUserInfo, setNewUserInfo}) => {
    const [IdFocus, setIdFocus] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);
    const [nicknameFocus, setNicknameFocus] = useState(false);
    return(
        <>
            <Div>
            <IdInput onFocus={() => setIdFocus(true)}
                          onBlur={() => newUserInfo.id ? null : setIdFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                id : e.target.value})
                            }
                           id="id"
                           ></IdInput>
                <IdLabel focus={IdFocus} htmlFor="id">아이디</IdLabel>
            </Div>
            <Div>
            <PwdInput onFocus={() => setPwdFocus(true)}
                          onBlur={() => newUserInfo.pwd ? null : setPwdFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                pwd : e.target.value})
                            }
                           type="password"
                           id="pwd"></PwdInput>
                <PwdLabel focus={pwdFocus} htmlFor="pwd">비밀번호</PwdLabel>
            </Div>
            <Div>
            <NicknameInput onFocus={() => setNicknameFocus(true)}
                          onBlur={() => newUserInfo.nickname ? null : setNicknameFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                nickname : e.target.value})
                            }
                           id="nickname"></NicknameInput>
                <NicknameLabel focus={nicknameFocus} htmlFor="nickname">닉네임</NicknameLabel>
            </Div>
        </>
    )
}

export default Div2items;