import styled from 'styled-components';

const Title = styled.p`
    margin-top: 3.2vh;
    font-size: 2.1rem;
    font-family: "SF";
    font-weight: 750;
`;

const SubTitle = styled.p`
    margin-top: 0.4vh;
    font-size: 1.1rem;
    font-family: "SF";
    font-weight: 450;
`; 

const Help = styled.p`
    margin-top: 0.4vh;
    font-size: 1.1rem;
    font-family: "SF";
    font-weight: 450;
`;

const IDFind = styled.span`
margin-left: 0.4rem;
font-size: 0.9rem;
font-family: "SF";
font-weight: 450;
color: rgb(0,112,201);
`;

const NameDiv = styled.div`
    margin-top:5px;
    width: 35vw;
    height: 10vh;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    border: 1px solid red;
`;

const LastName = styled.input`
    width: 43%;
    height: 64%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:20px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;

const LastNameLabel = styled.label`
    position: absolute;
    margin-right: 29.3%;
    color: rgba(100,100,100,0.8);
    z-index:1;
    transition:0.2s ease all;
`;

const Name = styled.input`
    width: 43%;
    height: 64%;
    background-color: rgba(255,255,255,0.8);
    border: 1px solid rgb(214,214,214);
    border-radius: 3px;
    padding-top:20px;
    padding-left: 10px;
    box-sizing: border-box;
    font-size: 17px;
`;

const NameLabel = styled.label`
position: absolute;
margin-left: 5%;
color: rgba(100,100,100,0.8);
z-index:1;
transition:0.2s ease all;
`;



const Div1items = () => {
    return(
        <>
            <Title>풋볼러브 아이디 생성</Title>
            <SubTitle>주변 사람들과 축구 한판 즐겨보세요.</SubTitle>
            <Help>이미 회원이십니까?<IDFind>찾아보기 ></IDFind></Help>
            <NameDiv>
                <LastName></LastName><LastNameLabel>성</LastNameLabel>
                <Name></Name><NameLabel>이름</NameLabel>
            </NameDiv>
        </>
    )
}


export default Div1items;