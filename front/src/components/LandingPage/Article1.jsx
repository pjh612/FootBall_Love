import styles from "../../css/Article.module.css";
export default function Article1() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.ad_wrapper}>
        <span className={styles.ad}>ALWAYS PLAY SOCCER</span>
        <span className={styles.sub_ad}>축구를 같이 할 동료를 구해보세요.</span>
        <span className={styles.third_ad}>
          365일 언제나 빠른대전에서 팀원들을 구할 수 있습니다.
        </span>
      </div>
      <img className={styles.soccer_img} src="img/soccer.png"></img>
    </div>
  );
}
