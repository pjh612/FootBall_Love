//부모 : flex, flex-direction: column;
import styled from 'styled-components';
import Div1items from './Div1items';
import Div2items from './Div2items';
import {useState} from 'react';
//Div1 : 성,이름  && 지역 && 생년월일 
const Div1 = styled.div`
    width: 70%;
    height: 35vh;
    border-bottom: 1px solid red;
    display:flex;
    flex-direction: column;
    align-items: center;
`;

const Div2 = styled.div`
    width: 70%;
    height: 35vh;
    border-bottom: 1px solid red;
    display:flex;
    flex-direction: column;
    align-items: center;
`;

const MainDiv = () => {
    const [newUserInfo, setNewUserInfo] = useState({
        id : null, 
        pwd : null,
        nickname : null,
        city : null,
        street : null,
        zipcode: null,
        lastName : null, // ok
        name : null, // ok
        brith : null, // ok 
        email : null,
        phone : null,
        type : null
    })
    return(
        <>
            <Div1>
                <Div1items newUserInfo={newUserInfo} setNewUserInfo={setNewUserInfo}></Div1items>
            </Div1>
            <Div2>
                <Div2items></Div2items>
            </Div2>
        </>
    );
}

export default MainDiv;