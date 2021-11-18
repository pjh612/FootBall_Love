import styled from "styled-components";

const Container =  styled.div`
display: block;
`;

const DateNav = styled.div`
    padding: 20px 0;
    position: sticky;
    z-index: 2;
    top:0;
    background-color: red;
    height: 87px;
`;

const TapWrap = styled.div`
    padding: 10px 30px;
    max-width: 1024px;
    
`;

export default function MainContainer() {
    return(
        <Container>
            <DateNav></DateNav>
        </Container>
    )
}