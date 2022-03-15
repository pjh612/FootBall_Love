import styles from '../../css/TeamList.module.css';

export default function TeamCard({ link, name, imageSrc }) {
  return (
    <>
      <div className={styles.cardWrapper} onClick={link}>
        <p className={styles.team_name}>{name}</p>
        <img className={styles.team_img} src={imageSrc}></img>
      </div>
    </>
  );
}
