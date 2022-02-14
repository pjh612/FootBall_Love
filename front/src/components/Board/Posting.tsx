import styles from './Posting.module.css';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

export default function Posting() {
  return (
    <div className={styles.list_wrapper}>
      <KeyboardArrowUpIcon className={styles.up_button}></KeyboardArrowUpIcon>
      <span className={styles.up_number}>123</span>
      <section className={styles.section_wrapper}>
        <div className={styles.row_flexbox}>
          <span className={styles.title}>이거 아는사람 있음?</span>
          <span className={styles.reply_count}>[4]</span>
        </div>
        <div className={styles.row_flexbox}>
          <span className={styles.write_time}>11시간 전</span>
          <span className={styles.nickname}>뻰티커</span>
        </div>
      </section>
      <div className={styles.image_wrapper}></div>
    </div>
  );
}
