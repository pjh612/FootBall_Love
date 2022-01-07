import styled from 'styled-components';
import Join from './Join';


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
                <Join></Join>
            </Div>
        </>
    )
}

export default JoinPage;