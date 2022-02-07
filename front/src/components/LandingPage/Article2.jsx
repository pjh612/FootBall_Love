import styles from "../../css/SubArticle.module.css";
import Ball from "../../asset/ball.svg";
import Comment from "../../asset/comment.svg";
import HandShake from "../../asset/handshake.svg";

export default function Article2() {
  return (
    <div className={styles.wrapper}>
      <span className={styles.title}>FootBallLove를 이용해야 하는 이유</span>
      <div className={styles.icon_wrapper}>
        <div className={styles.icon_wrapper_one}>
          <img className={styles.icon} src={Ball}></img>
          <span className={styles.article_title}>자유로운 축구</span>
          <span className={styles.article_sub_title}>
            365일 어디서든 이용가능
          </span>
        </div>
        <div className={styles.icon_wrapper_one}>
          <img className={styles.icon} src={Comment}></img>
          <span className={styles.article_title}>팀 게시판</span>
          <span className={styles.article_sub_title}>
            팀을 만들어 소통하세요.
          </span>
        </div>
        <div className={styles.icon_wrapper_one}>
          <img className={styles.icon} src={HandShake}></img>
          <span className={styles.article_title}>다른팀과 매치</span>
          <span className={styles.article_sub_title}>
            같이 축구 경기를 할 상대를 구하세요.
          </span>
        </div>
      </div>
    </div>
  );
}
