import styled from 'styled-components';
import {useState} from 'react';

// email, phone, 주소 , submit Button

const Div = styled.div`
    margin-top:5px;
    width: 35vw;
    height: 10vh;
    display: flex;
    justify-content: flex-start;
    align-items: center;
`;


const PhoneLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const PhoneInput = styled.input`
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

const EmailLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const EmailInput = styled.input`
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






const Div3items = ({newUserInfo, setNewUserInfo}) => {
    const [phoneFocus, setPhoneFocus] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);
    // const [nicknameFocus, setNicknameFocus] = useState(false);
    return(
        <>
            <Div>
            <PhoneInput onFocus={() => setPhoneFocus(true)}
                          onBlur={() => newUserInfo.phone ? null : setPhoneFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                phone : e.target.value})
                            }
                           id="phone"
                           ></PhoneInput>
                <PhoneLabel focus={phoneFocus} htmlFor="phone">휴대폰 번호</PhoneLabel>
            </Div>
            <Div>
            <EmailInput onFocus={() => setEmailFocus(true)}
                          onBlur={() => newUserInfo.email ? null : setEmailFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                email : e.target.value})
                            }
                           id="email"></EmailInput>
                <EmailLabel focus={emailFocus} htmlFor="email">이메일</EmailLabel>
            </Div>
            {/* <Div>
            <NicknameInput onFocus={() => setNicknameFocus(true)}
                          onBlur={() => newUserInfo.nickname ? null : setNicknameFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                nickname : e.target.value})
                            }
                           id="nickname"></NicknameInput>
                <NicknameLabel focus={nicknameFocus} htmlFor="nickname">닉네임</NicknameLabel>
            </Div> */}
        </>
    )
}

export default Div3items;