import React, { useState } from "react";
import LoginModal from "../Login/LoginModal";
import JoinModal from "../Join/JoinModal";
import { Link } from "react-router-dom";
import SportsBasketballIcon from "@mui/icons-material/SportsBasketball";
import UserAvatar from "./UserAvatar";
import { useSelector } from "react-redux";
import {
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
} from "./styled";

export default function Navbar() {
  const user = useSelector((state) => state.userReducer.user);

  const [openLoginModal, setOpenLoginModal] = useState(false);
  const [openJoinModal, setOpenJoinModal] = useState(false);
  const openLoginModalFn = () => {
    setOpenLoginModal(true);
  };
  const closeLoginModal = () => {
    setOpenLoginModal(false);
  };
  const openJoinModalFn = () => {
    setOpenJoinModal(true);
  };
  const closeJoinModal = () => {
    setOpenJoinModal(false);
  };

  return (
    <NavbarContainer>
      <NavBarContainerCenter>
        <NavBarContainerLogo>
          <Link to="/" style={{ textDecoration: "none" }}>
            <Logo>
              <SportsBasketballIcon sx={{ mr: 1 }}></SportsBasketballIcon>
            </Logo>
            <Name>풋볼러브</Name>
          </Link>
        </NavBarContainerLogo>
        <NavBarContainerUser>
          {user ? (
            <UserAvatar></UserAvatar>
          ) : (
            <GoIn>
              <Login onClick={() => openLoginModalFn()}>로그인</Login>
              {openLoginModal && (
                <LoginModal CloseModal={closeLoginModal}></LoginModal>
              )}
              <And>또는</And>
              <Join onClick={() => openJoinModalFn()}>회원가입</Join>
              {openJoinModal && (
                <JoinModal CloseModal={closeJoinModal}></JoinModal>
              )}
            </GoIn>
          )}
        </NavBarContainerUser>
      </NavBarContainerCenter>
    </NavbarContainer>
  );
}
