import styled from 'styled-components';
import SubBar from './SubBar';
import MainDiv from './MainDiv';

const Div = styled.div`
    width:100vw;
    height:93vh;
    overflow: hidden;
    display:flex;
    flex-direction : column;
    align-items: center;
`;

const JoinPage = () => {
    return(
        <>
            <Div>
                <SubBar></SubBar>
                <MainDiv></MainDiv>
            </Div>
        </>
    )
}

export default JoinPage;