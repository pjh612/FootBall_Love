import React, {useState} from "react";
import styled from "styled-components";
import LoginModal from './AuthComponents/LoginModal';

const NavbarContainer = styled.div`
  margin: 0;
  width:100%;
  height:56px;
  display:flex;
  justify-content: center;
  align-items: center;
`;

const NavBarContainerCenter = styled.div`
    height: 56px;
    padding: 0 20px;
    max-width: 1024px;
    width: 1024px;
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const NavBarContainerLogo = styled.div`
    width: 100px;
    height: 21px;
    
    
`;

const Logo = styled.span`
line-height: 24px;
font-family: 'Black Han Sans', sans-serif;
font-size: 24px;
cursor: pointer;
`;

const NavBarContainerUser = styled.div`
    width: 151px;
    height: 24px;
    display:flex;
    align-items: center; 
    justify-content:space-between;   
`;

const GoIn = styled.div`
    width: 114px;
    height:19px;
    
`;

const SlideBarTrigerDiv = styled.div`
    width: 32px;
    height: 24px;
    
    padding-left: 8px;
    cursor: pointer;
`;

const Login = styled.span`
    width: 36.34px;
    height: 17px;
    color: #3540A5;
    font-size: 14px;
    cursor: pointer;
`;

const And = styled.span`
width: 20.77px;
height: 15px;
color: #999;
font-size: 12px;
margin-right: 3px;
margin-left: 3px;   
`;

const Join = styled.span`
    width: 36.34px;
    height: 17px;
    color: #3540A5;
    font-size: 14px;
    cursor: pointer;
`;

const MoreIconWrap = styled.div`
    display: flex;
    
    padding: 10px 0;
`;

const MoreIcon = styled.div`
width: 4px;
height: 4px;
background-color: #222836;
margin: 0 2.9px;
border-radius: 10px;
display: inline-block;
`;



export default function Navbar() {
    const [openLoginModal, setOpenLoginModal] = useState(false);
    const openModal = () => {
        setOpenLoginModal(true);
    }
    const closeModal = () => {
        setOpenLoginModal(false);
    }
    return(<NavbarContainer>
        <NavBarContainerCenter>
            <NavBarContainerLogo>
                <Logo>풋볼러브</Logo>
            </NavBarContainerLogo>
            <NavBarContainerUser>
                <GoIn>
                    <Login onClick={() => openModal()}>로그인</Login>
                    {openLoginModal && <LoginModal CloseModal={closeModal}></LoginModal>}
                    <And>또는</And>
                    <Join>회원가입</Join>
                </GoIn>
                <SlideBarTrigerDiv>
                    <MoreIconWrap>
                        <MoreIcon></MoreIcon>
                        <MoreIcon></MoreIcon>
                        <MoreIcon></MoreIcon>
                    </MoreIconWrap>
                </SlideBarTrigerDiv>
            </NavBarContainerUser>
        </NavBarContainerCenter>
    </NavbarContainer>)
}