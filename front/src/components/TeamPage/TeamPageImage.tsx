import styles from '../../css/TeamPage.module.css';

const TeamPageImage = ({ teamUri }: any) => {
  return (
    <>
      <img
        className={styles.teamImage}
        src="https://i.annihil.us/u/prod/marvel/i/mg/c/f0/6169eecb78c4c/clean.jpg"
      ></img>
    </>
  );
};

export default TeamPageImage;
