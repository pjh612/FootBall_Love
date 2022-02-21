import WriteButton from './WriteButton';
import styles from './Posting.module.css';
export default function Board() {
  return (
    <div className={styles.wrapper}>
      <WriteButton></WriteButton>
    </div>
  );
}
