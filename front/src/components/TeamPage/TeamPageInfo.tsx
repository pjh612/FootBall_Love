import styles from '../../css/TeamPage.module.css';
// { teamName, teamMembers, boards, introduce }
const TeamPageInfo = () => {
  return (
    <div className={styles.info_wrapper}>
      <span className={styles.title}>베놈(2021)</span>

      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 맴버:</span>
        <span className={styles.introduce}>박민창 송민호 강민주 강민지 김아랑</span>
      </div>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 소개:</span>
        <span className={styles.introduce}>팀소개입니다팀팀</span>
      </div>
    </div>
  );
};

export default TeamPageInfo;
