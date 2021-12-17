import styled from 'styled-components';
import SubBar from './SubBar';
import MainDiv from './MainDiv';

const Div = styled.div`
    width:100vw;
    display:flex;
    flex-direction : column;
    align-items: center;
    overflow: auto;
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