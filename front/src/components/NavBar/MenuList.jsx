import styles from "../../css/NavBar.module.css";
import { useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useUser from "../../hooks/useUser";

export default function MenuList({ active }) {
  const buttonBox = useRef();
  const navigate = useNavigate();
  const toggle = () => buttonBox.current.classList.toggle(`${styles.active}`);
  useEffect(() => {
    toggle();
  }, [active]);

  const user = useUser();

  return (
    <div ref={buttonBox} className={`${styles.menu_button_box_wrapper}`}>
      <button
        onClick={() => navigate("./match")}
        className={styles.menu_button_style}
      >
        빠른대전
      </button>
      <button
        onClick={() => navigate("./board")}
        className={styles.menu_button_style}
      >
        커뮤니티
      </button>
      {user ? (
        <button
          onClick={() => navigate("./myPage")}
          className={styles.menu_button_style}
        >
          나의 프로필
        </button>
      ) : (
        <button
          onClick={() => navigate("./login")}
          className={styles.menu_button_style}
        >
          Sign in
        </button>
      )}
    </div>
  );
}
