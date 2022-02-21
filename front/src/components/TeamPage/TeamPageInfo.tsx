import styles from '../../css/TeamPage.module.css';

const TeamPageInfo = ({ teamInfo }) => {
  return (
    <div className={styles.info_wrapper}>
      <span className={styles.title}>{teamInfo.name ? teamInfo.name : '로딩중'}</span>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 맴버:</span>
        <span className={styles.introduce}>박민창 송민호 강민주 강민지 김아랑</span>
      </div>
      <div className={styles.parse_wrapper}>
        <span className={styles.name}>팀 소개:</span>
        <span className={styles.introduce}>{teamInfo.introduce ? teamInfo.introduce : '로딩중'}</span>
      </div>
    </div>
  );
};

export default TeamPageInfo;
