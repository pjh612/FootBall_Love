//부모 : flex, flex-direction: column;
import styled from 'styled-components';
import Div1items from './Div1items';
//Div1 : 성,이름  && 지역 && 생년월일 
const Div1 = styled.div`
    width: 70%;
    height: 50vh;
    border-bottom: 1px solid red;
    display:flex;
    flex-direction: column;
    align-items: center;
`;

const MainDiv = () => {
    return(
        <>
            <Div1>
                <Div1items></Div1items>
            </Div1>
        </>
    );
}

export default MainDiv;