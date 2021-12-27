import styled from 'styled-components';
//Submit Button
const SubmitButton = styled.button`
    width: 62px;
    height: 36.5px;
    cursor: pointer;
    font-size: 18px;
    border: 1px solid #07c;
    border-radius: 2px;
    background: linear-gradient(#42a1ec,#0070c9);
    color: white;
`;


const Div4items = ({newUserInfo, submitJoinData}) => {
    return(
        <>
            <SubmitButton onClick={() => submitJoinData(newUserInfo)}>계속</SubmitButton>
        </>
    )
}

export default Div4items;