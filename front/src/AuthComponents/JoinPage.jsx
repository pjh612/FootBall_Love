import styled from 'styled-components';

const WrapperDiv = styled.div`
    overflow: hidden;
    display:flex;
    justify-content: center;
    align-items: center;
    backgrond : black;
`;

const MainDiv = styled.div`
    width: 70vw;
    height: 100vh;
    position: absolute;
    z-index: 10;
    
`;

const JoinPage = () => {
    return(
        <>
            <WrapperDiv>
                <MainDiv></MainDiv>
            </WrapperDiv>
        </>    
    )
}

export default JoinPage;