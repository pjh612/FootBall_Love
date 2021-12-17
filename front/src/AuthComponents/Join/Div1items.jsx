import styled from 'styled-components';
import {useState} from 'react';
// Title , Name, LastName, BirthDay 

const Title = styled.p`
    margin-top: 3.2vh;
    font-size: 2.1rem;
    font-family: "SF";
    font-weight: 750;
`;

const SubTitle = styled.p`
    margin-top: 0.4vh;
    font-size: 1.1rem;
    font-family: "SF";
    font-weight: 450;
`; 

const Help = styled.p`
    margin-top: 0.4vh;
    font-size: 1.1rem;
    font-family: "SF";
    font-weight: 450;
`;

const IDFind = styled.span`
margin-left: 0.4rem;
font-size: 0.9rem;
font-family: "SF";
font-weight: 450;
color: rgb(0,112,201);
`;

const NameDiv = styled.div`
    margin-top:5px;
    width: 35vw;
    height: 10vh;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    border: 1px solid red;
`;

const LastName = styled.input`
    width: 43%;
    height: 64%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
    margin-left: 5%;
`;

const LastNameLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const NameLabel = styled.label`
    position: absolute;
    margin-left: 19%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition: 0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3 : 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const Name = styled.input`
    width: 43%;
    height: 64%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:18px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
    margin-left: 4%;
`;

const BirthDayDiv = styled.div`
    margin-top: -10px;
    width: 35vw;
    height: 10vh;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    border: 1px solid red;
`;

const BirthDayLabel = styled.label`
    position: absolute;
    margin-left: 2.4%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.3s ease all;
    margin-bottom: ${props => props.focus ? 1.3: 0}%;
    font-size: ${props => props.focus ? 8 : 16}px;
`;

const BirthDayInput = styled.input`
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

const Div1items = ({newUserInfo, setNewUserInfo}) => {
    const [nameFocus, setNameFocus] = useState(false);
    const [LastNameFocus, setLastNameFocus] = useState(false);
    const [birthDayFocus, setBirthDayFocus] = useState(false);
    return(
        <>
            <Title>풋볼러브 아이디 생성</Title>
            <SubTitle>주변 사람들과 즐기는 짜릿한 한판 승부, 풋볼러브에서!</SubTitle>
            <Help>이미 회원이십니까?<IDFind>찾아보기 ></IDFind></Help>
            <NameDiv>
                <LastName onFocus={() => setLastNameFocus(true)}
                          onBlur={() => newUserInfo.lastName ? null : setLastNameFocus(false)}
                          onChange={(e) => 
                            setNewUserInfo({...newUserInfo, 
                                lastName : e.target.value})
                            }
                           id="lastname"></LastName>
                <LastNameLabel focus={LastNameFocus} htmlFor="lastname">성</LastNameLabel>
                <Name onFocus={() => setNameFocus(true)}
                      onBlur={() => newUserInfo.name ? null : setNameFocus(false)}
                      onChange={(e) => 
                        setNewUserInfo({...newUserInfo, 
                            name : e.target.value})
                        }
                id="name"></Name>
                <NameLabel focus={nameFocus} htmlFor="name">이름</NameLabel>
            </NameDiv>
            <BirthDayDiv>
                <BirthDayInput 
                    onFocus={() => setBirthDayFocus(true)}
                    onBlur={() => newUserInfo.birth ? null : setBirthDayFocus(false)}
                    onChange={(e) => {
                        let inputInfo = e.target.value;
                      setNewUserInfo({...newUserInfo, 
                          birth : inputInfo});
                      }
                    }
                    id = "birthday"
                    type="text"
                    maxLength="8"
                ></BirthDayInput>
                <BirthDayLabel focus={birthDayFocus} htmlFor="birthday">생년월일 yyyymmdd</BirthDayLabel>
            </BirthDayDiv>
        </>
    )
}


export default Div1items;