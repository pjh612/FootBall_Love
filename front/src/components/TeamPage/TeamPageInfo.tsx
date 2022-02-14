import styles from '../../css/TeamPage.module.css';

const TeamPageInfo = () => {
  return (
    <div className={styles.info_wrapper}>
      <span className={styles.title}>베놈(2021)</span>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>대표:</span>
        <span className={styles.introduce}>김민준</span>
      </div>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 생성일:</span>
        <span className={styles.introduce}>2022.1.2</span>
      </div>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 소개:</span>
        <span className={styles.introduce}>팀소개입니다팀팀</span>
      </div>
    </div>
  );
};

export default TeamPageInfo;
