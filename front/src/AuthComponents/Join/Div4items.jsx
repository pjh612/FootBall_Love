import styled from 'styled-components';
//Submit Button
const SubmitButton = styled.button`
    cursor: pointer;
`;


const Div4items = ({newUserInfo, submitJoinData}) => {
    return(
        <>
            <SubmitButton onClick={() => submitJoinData(newUserInfo)}>제출</SubmitButton>
        </>
    )
}

export default Div4items;