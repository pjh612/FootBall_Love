import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const NavbarContainer = styled.div`
  margin: 0;
  width: 100%;
  height: 69px;
  padding: 0 20px;
  line-height: 40px;
  display: flex;
  justify-content: space-between;
`;

const NavbarWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
  padding: 14px 20px;
  max-width: 1024px;
  width: 1024px;
`;

const NavbarItem = styled.div`
  margin: 0;
  padding: 0;
  border: 0;
  vertical-align: baseline;
  height: 41px;
`;

const Item = styled.span`
  margin-left: 0;
  font-size: 18px;
  margin: 0 20px;
  cursor: pointer;
  font-weight: 700;
`;

export default function Navbar2() {
  return (
    <NavbarContainer>
      <NavbarWrapper>
        <NavbarItem>
          <Item>소셜 매치</Item>
          <Item>구장 예약</Item>
          <Link
            to="/teammake"
            style={{ textDecoration: "none", color: "black" }}
          >
            <Item>팀 만들기</Item>
          </Link>
        </NavbarItem>
      </NavbarWrapper>
    </NavbarContainer>
  );
}
