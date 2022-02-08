import styles from "../../css/TeamList.module.css";
import Temp from "../../asset/team.svg";
import { useTeam } from "../../hooks/useUser";

export default function TeamList() {
  const teams = useTeam();
  let teamList;

  if (teams.size() === 0) {
    teamList = null;
  } else {
    teamList = teams.map((team) => {
      let imageSrc = team.imageSrc ? team.imageSrc : Temp;
      return (
        <div className={styles.team_one_wrapper}>
          <img className={styles.team_image} src={imageSrc}></img>
          <span className={styles.team_name}>{team.teamName}</span>
        </div>
      );
    });
  }

  return <div className={styles.wrapper}>{teamList}</div>;
}
