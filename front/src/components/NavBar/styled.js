import styled from "styled-components";

const NavbarContainer = styled.div`
  margin: 0;
  width: 100%;
  height: 56px;
  display: flex;
  // position: fixed;
  // top: 0;
  justify-content: center;
  align-items: center;
  background: #064635;
  // z-index: 10000;
`;

const NavBarContainerCenter = styled.div`
  height: 56px;
  padding: 0 20px;
  width: 1024px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const NavBarContainerLogo = styled.div`
  width: 200px;
  height: 100%;
  display: flex;
  align-items: center;
`;

const Logo = styled.span`
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 800;
  line-height: 100%;
  font-size: 20px;
  vertical-align: middle;
  color: #f5f5f7;
  cursor: pointer;
`;
const Name = styled.span`
  font-family: "Nanum Gothic", sans-serif;
  font-weight: 800;
  line-height: 100%;
  font-size: 20px;
  vertical-align: middle;
  color: #f5f5f7;
  cursor: pointer;
`;

const NavBarContainerUser = styled.div`
  width: 151px;
  height: 24px;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: space-between;
`;

const GoIn = styled.div`
  width: 150px;
  height: 19px;
`;

const Login = styled.span`
  width: 36.34px;
  height: 17px;
  color: #f5f5f7;
  font-size: 14px;
  cursor: pointer;
`;

const And = styled.span`
  width: 20.77px;
  height: 15px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 12px;
  margin-right: 3px;
  margin-left: 3px;
`;

const Join = styled.span`
  width: 36.34px;
  height: 17px;
  color: #f5f5f7;
  font-size: 14px;
  cursor: pointer;
`;

export {
  NavbarContainer,
  NavBarContainerCenter,
  NavBarContainerLogo,
  Logo,
  Name,
  NavBarContainerUser,
  GoIn,
  Login,
  And,
  Join,
};
