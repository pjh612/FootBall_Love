import React from "react";
import styled from "styled-components";

import styles from "../../css/NavBar.module.css";
import { useState } from "react";
import Logo from "./Logo";
import MenuList from "./MenuList";
import Hambergur from "../../asset/hamburger.svg";

export default function NavBar() {
  const [active, setActive] = useState(false);
  const toggle = () => {
    setActive(!active);
  };

  return (
    <div className={styles.wrapper}>
      <Logo></Logo>
      <MenuList active={active} setActive={setActive}></MenuList>
      <button onClick={toggle} className={styles.hamburger}>
        <img src={Hambergur}></img>
      </button>
    </div>
  );
}
