import { useLogin } from '../../hooks/useUser';
import styles from '../../css/NavBar.module.css';
import { useState } from 'react';
import Logo from './Logo';
import MenuList from './MenuList';
import Hambergur from '../../asset/hamburger.svg';

export default function NavBar() {
  const [active, setActive] = useState(false);
  const toggle = () => {
    setActive(!active);
  };
  const isLogin = useLogin();

  return (
    <div className={styles.wrapper}>
      <Logo></Logo>
      <MenuList isLogin={isLogin} active={active} setActive={setActive}></MenuList>
      <button onClick={toggle} className={styles.hamburger}>
        <img src={Hambergur}></img>
      </button>
    </div>
  );
}
