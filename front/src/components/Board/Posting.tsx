import styles from './Posting.module.css';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

export default function Posting({ post }) {
  return (
    <div className={styles.list_wrapper}>
      <KeyboardArrowUpIcon className={styles.up_button}></KeyboardArrowUpIcon>
      <span className={styles.up_number}>{post.likeCount}</span>
      <section className={styles.section_wrapper}>
        <div className={styles.row_flexbox}>
          <span className={styles.title}>{post.title}</span>
          <span className={styles.reply_count}>{post.comments.length}</span>
        </div>
        <div className={styles.row_flexbox}>
          <span className={styles.write_time}>{post.createdDate}</span>
          <span className={styles.nickname}>{post.authorId}</span>
        </div>
      </section>
      <div className={styles.image_wrapper}></div>
    </div>
  );
}
