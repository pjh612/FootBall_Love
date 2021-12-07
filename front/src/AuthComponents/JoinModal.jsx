import styled from "styled-components";
import {Link} from 'react-router-dom';

const BackGround = styled.div`
position: fixed;
top:0;
left:0;
background-color: rgba(0,0,0,0.4);
z-index: 100;
height: 100vh;
width: 100%;
overflow-y: hidden;
`;

const ModalDiv = styled.div`
    position: absolute;
    background: white;
    top: 50%;
    left: 50%;
    width: 400px;
    height: 230px;
    transform: translate(-50%, -50%);
    border-radius: 10px;
    z-index: 105;
    text-align: center;
`;

const KakaoLogin = styled.div`
    display: block;
    position: relative;
    width: 85%;
    background: #FEE500;
    font-size: 16px;
    padding: 14px 14px;
    margin: 10px auto;
    text-align: center;
    border-radius: 12px;
`;

const FootBallLoveLogin = styled.div`
    display: block;
    position: relative;
    width: 85%;
    font-size: 14px;
    padding: 14px 14px;
    margin: 10px auto;
    text-align: center;
    color: black;

`;

const Span = styled.span`
    font-size: 20px;
    margin-top: 30px;
    display: inline-block;
    margin-bottom: 30px;
`;


function JoinModal({CloseModal}) {

    return(
        <>
        <ModalDiv>
            <Span>축구는 <b>풋볼러브</b>에서</Span>
            <KakaoLogin>카카오계정으로 가입하기</KakaoLogin>
            <FootBallLoveLogin>이메일로 가입할래요</FootBallLoveLogin>
        </ModalDiv>
        <BackGround onClick={() => CloseModal()}></BackGround>
        </>
    )
}

export default JoinModal;