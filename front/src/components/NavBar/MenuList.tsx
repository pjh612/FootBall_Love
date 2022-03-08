import styles from '../../css/NavBar.module.css';
import { useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../../hooks/useUser';

export default function MenuList({ active, setActive, isLogin }: any) {
  const buttonBox = useRef<HTMLDivElement>(null);
  const navigate = useNavigate();
  const toggle = () => {
    if (buttonBox) {
      buttonBox.current.classList.toggle(`${styles.active}`);
    }
  };

  useEffect(() => {
    toggle();
  }, [active]);

  const route = (des) => {
    navigate(`./${des}`);
  };

  return (
    <div ref={buttonBox} className={`${styles.menu_button_box_wrapper}`}>
      <button
        onClick={() => {
          setActive(!active);
          route('match');
        }}
        className={styles.menu_button_style}
      >
        빠른대전
      </button>
      <button
        onClick={() => {
          setActive(!active);
          route('community');
        }}
        className={styles.menu_button_style}
      >
        커뮤니티
      </button>
      <button
        onClick={() => {
          setActive(!active);
          route('teammake');
        }}
        className={styles.menu_button_style}
      >
        팀만들기
      </button>
      {isLogin ? (
        <button
          onClick={() => {
            setActive(!active);
            route('myPage');
          }}
          className={styles.menu_button_style}
        >
          나의 프로필
        </button>
      ) : (
        <button
          onClick={() => {
            setActive(!active);
            navigate('./login');
          }}
          className={styles.menu_button_style}
        >
          Sign in
        </button>
      )}
    </div>
  );
}
