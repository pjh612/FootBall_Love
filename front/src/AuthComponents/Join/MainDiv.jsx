//부모 : flex, flex-direction: column;
import styled from 'styled-components';
import Div1items from './Div1items';
import Div2items from './Div2items';
import Div3items from './Div3items';
import Div4items from './Div4items';
import {useState} from 'react';
import {sendJoinData} from '../../axios/axios.js';
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

const Div3 = styled.div`
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
        address:{
            city : '서울',
            street : '중랑구 망우로 173',
            zipcode: '10234'
        },
        lastName : null, // ok
        name : null, // ok
        brith : null, // ok 
        email : null, // ok
        phone : null, // ok
        type : 'NORMAL'
    })

    const submitJoinData = (userInfo) => {
        const data = Object.assign({},userInfo);
        var input = JSON.parse('{"date":{"year":2016,"month":"NOVEMBER","dayOfMonth":15,"monthValue":11,"dayOfWeek":"TUESDAY","era":"CE","dayOfYear":320,"leapYear":true,"chronology":{"id":"ISO","calendarType":"iso8601"}}}');
            
        var day = input.date.dayOfMonth;
        var month = input.date.monthValue - 1; // Month is 0-indexed
        var year = input.date.year;
        
        var date = new Date(Date.UTC(year, month, day));
        data.name = data.lastName + data.name;
        delete data.lastName;
        data.birth = date;
        sendJoinData(data)
        .then((res) => {
            console.log(res);
        })
        .catch((err) => {
            console.error(err);
        })
    }
    return(
        <>
            <Div1>
                <Div1items newUserInfo={newUserInfo} setNewUserInfo={setNewUserInfo}></Div1items>
            </Div1>
            <Div2>
                <Div2items newUserInfo={newUserInfo} setNewUserInfo={setNewUserInfo}></Div2items>
            </Div2>
            <Div3>
                <Div3items newUserInfo={newUserInfo} setNewUserInfo={setNewUserInfo}></Div3items>
                <Div4items newUserInfo={newUserInfo} submitJoinData={submitJoinData}></Div4items>
            </Div3>
        </>
    );
}

export default MainDiv;