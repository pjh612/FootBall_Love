import { Link } from "react-router-dom";
import {
  Span,
  FootBallLoveLogin,
  KakaoLogin,
  ModalDiv,
  BackGround,
} from "./styled";

function JoinModal({ CloseModal }) {
  return (
    <>
      <ModalDiv>
        <Span>
          축구는 <b>풋볼러브</b>에서
        </Span>
        <KakaoLogin>카카오계정으로 가입하기</KakaoLogin>
        <Link to="/join" onClick={() => CloseModal()}>
          <FootBallLoveLogin>이메일로 가입할래요</FootBallLoveLogin>
        </Link>
      </ModalDiv>
      <BackGround onClick={() => CloseModal()}></BackGround>
    </>
  );
}

export default JoinModal;
