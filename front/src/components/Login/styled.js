import styled from "styled-components";

const Div = styled.div`
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: auto;
  overflow-x: hidden;
`;

// flex-direction 을 row 로 해주기 위한 div
const Div2 = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

const BackGround = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 100;
  height: 100%;
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
  background: #fee500;
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
  font-size: 16px;
  padding: 14px 14px;
  margin: 10px auto;
  text-align: center;
`;

const Span = styled.span`
  font-size: 20px;
  margin-top: 30px;
  display: inline-block;
  margin-bottom: 30px;
`;

export { Div, Div2, BackGround, ModalDiv, Span, FootBallLoveLogin, KakaoLogin };
