import { Link } from "react-router-dom";
import {
  BackGround,
  ModalDiv,
  Span,
  FootBallLoveLogin,
  KakaoLogin,
} from "./styled";

function LoginModal({ CloseModal }) {
  return (
    <>
      <ModalDiv>
        <Span>
          축구는 <b>풋볼러브</b>에서
        </Span>
        <KakaoLogin>카카오로 로그인하기</KakaoLogin>
        <Link to="/login" onClick={() => CloseModal()}>
          <FootBallLoveLogin>아이디 또는 이메일로 로그인</FootBallLoveLogin>
        </Link>
      </ModalDiv>
      <BackGround onClick={() => CloseModal()}></BackGround>
    </>
  );
}

export default LoginModal;
