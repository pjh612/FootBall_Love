import styles from '../../css/NavBar.module.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Logo() {
  const navigate = useNavigate();
  const changeLogoName = () => {
    const match = window.matchMedia('screen and (max-width: 768px)');
    if (match.matches) {
      return 'FBL';
    } else {
      return 'FootBallLove';
    }
  };
  // media query
  // 현재 width 에 따라 Logo 가 footballLove 혹은 fbl 이 됨.
  const [logoName, setLogoName] = useState(changeLogoName());

  const changeLogoState = () => {
    setLogoName(changeLogoName());
  };

  useEffect(() => {
    window.addEventListener('resize', changeLogoState);
    return () => {
      window.removeEventListener('resize', changeLogoState);
    };
  }, []);
  return (
    <span onClick={() => navigate('/')} className={styles.logo}>
      {logoName}
    </span>
  );
}
