import React, {useState} from "react";
import styled from "styled-components";
import LoginModal from './AuthComponents/LoginModal';
import JoinModal from './AuthComponents/Join/JoinModal';
import {Link} from 'react-router-dom'

const NavbarContainer = styled.div`
  margin: 0;
  width:100%;
  height:56px;
  display:flex;
  justify-content: center;
  align-items: center;
  background: rgba(0,0,0,0.8);
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
    width: 100px;
    height: 21px;
`;

const Logo = styled.span`
line-height: 24px;
font-family: 'Black Han Sans', sans-serif;
font-size: 24px;
color: #f5f5f7;
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
    color: #f5f5f7;
    font-size: 14px;
    cursor: pointer;
`;

const And = styled.span`
width: 20.77px;
height: 15px;
color: rgba(255,255,255,0.8);
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

const MoreIconWrap = styled.div`
    display: flex;
    padding: 10px 0;
`;

const MoreIcon = styled.div`
width: 4px;
height: 4px;
background-color: white;
margin: 0 2.9px;
border-radius: 10px;
display: inline-block;
`;

// const IconBox = styled.div`
//     width: 10px;
//     height: 10px;
//     // margin-top: 5px;
//     // margin-left: 15px;
//     // margin-right: 10px;
//     // margin-bottom: 5px;
//     background-image : url(${props => props.src ? props.src : null});
// `;

export default function Navbar() {
    const [openLoginModal, setOpenLoginModal] = useState(false);
    const [openJoinModal, setOpenJoinModal] = useState(false);

    const openLoginModalFn = () => {
        setOpenLoginModal(true);
    }
    const closeLoginModal = () => {
        setOpenLoginModal(false);
    }
    const openJoinModalFn = () => {
        setOpenJoinModal(true);
    }
    const closeJoinModal = () => {
        setOpenJoinModal(false);
    }

    return(<NavbarContainer>
        <NavBarContainerCenter>
            <NavBarContainerLogo>
                <Link to='/'  style={{ textDecoration: 'none' }}><Logo>풋볼러브</Logo></Link>
            </NavBarContainerLogo>
            <NavBarContainerUser>
                <GoIn>
                    <Login onClick={() => openLoginModalFn()}>로그인</Login>
                    {openLoginModal && <LoginModal CloseModal={closeLoginModal}></LoginModal>}
                    <And>또는</And>
                    <Join onClick={() => openJoinModalFn()}>회원가입</Join>
                    {openJoinModal && <JoinModal CloseModal={closeJoinModal}></JoinModal>}
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