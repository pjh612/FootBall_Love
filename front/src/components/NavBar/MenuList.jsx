import styles from "../../css/NavBar.module.css";
import { useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../hooks/useUser";

export default function MenuList({ active, setActive }) {
  const buttonBox = useRef();
  const navigate = useNavigate();
  const toggle = () => buttonBox.current.classList.toggle(`${styles.active}`);
  useEffect(() => {
    toggle();
  }, [active]);

  const user = useUser();

  const route = (des) => {
    // 열려있는 메뉴리스트를 닫아줌

    navigate(`./${des}`);
  };

  return (
    <div ref={buttonBox} className={`${styles.menu_button_box_wrapper}`}>
      <button
        onClick={() => {
          setActive(!active);
          route("match");
        }}
        className={styles.menu_button_style}
      >
        빠른대전
      </button>
      <button
        onClick={() => {
          setActive(!active);
          route("board");
        }}
        className={styles.menu_button_style}
      >
        커뮤니티
      </button>
      <button
        onClick={() => {
          setActive(!active);
          route("teammake");
        }}
        className={styles.menu_button_style}
      >
        팀만들기
      </button>
      {user ? (
        <button
          onClick={() => {
            setActive(!active);
            route("myPage");
          }}
          className={styles.menu_button_style}
        >
          나의 프로필
        </button>
      ) : (
        <button
          onClick={() => {
            setActive(!active);
            navigate("./login");
          }}
          className={styles.menu_button_style}
        >
          Sign in
        </button>
      )}
    </div>
  );
}
