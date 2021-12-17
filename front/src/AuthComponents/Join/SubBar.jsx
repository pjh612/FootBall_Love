// 부모: flex 박스, flex-direction: column;
import styled from 'styled-components';

const Div = styled.div`
    width:100%;
    height: 7vh;
    border: 1px solid red;
    display: flex;
    justify-content: space-between;
`;

const LeftDiv = styled.div`
    width: 14%;
    height: 100%;
    border: 1px solid red;
    margin-left: 14%;
`;

const RightDiv = styled.div`
    width: 20%;
    height: 100%;
    border: 1px solid red;
    margin-right: 14%;
    display:flex;
    justify-content: space-between;
`;

const SubBar = () => {
    return(
        <>
            <Div>
                <LeftDiv></LeftDiv>
                <RightDiv></RightDiv>
            </Div>
        </>
    )
}

export default SubBar;