import CheckLogin from "../../components/CheckLogin";
import styles from "../../css/MyPage.module.css";
import SignOut from "../../asset/signOut.svg";
import Team from "../../asset/team.svg";
import User from "../../asset/user.svg";
import { useNavigate } from "react-router-dom";

export default function MyPage() {
  const navigate = useNavigate();
  return (
    <CheckLogin>
      <div className={styles.wrapper}>
        <div className={styles.select_wrapper}>
          <div
            className={styles.select_one_wrapper}
            onClick={() => navigate("/profile")}
          >
            <img className={styles.select_icon} src={User}></img>
            <span className={styles.select_title}>나의 프로필</span>
          </div>
          <div
            className={styles.select_one_wrapper}
            onClick={() => navigate("/teampage")}
          >
            <img className={styles.select_icon} src={Team}></img>
            <span className={styles.select_title}>나의 팀</span>
          </div>
          <div
            className={styles.select_one_wrapper}
            onClick={() => navigate("/logout")}
          >
            <img className={styles.select_icon} src={SignOut}></img>
            <span className={styles.select_title}>로그아웃 하기</span>
          </div>
        </div>
      </div>
    </CheckLogin>
  );
}
