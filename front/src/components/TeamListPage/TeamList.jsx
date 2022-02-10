import styles from "../../css/TeamList.module.css";
import Temp from "../../asset/team.svg";
import { useTeam } from "../../hooks/useUser";

export default function TeamList() {
  const teams = useTeam();
  console.log(teams);
  let teamList = null;

  if (teams.length === 0) {
    console.log("팀이 아직 없습니다.");
  } else {
    teamList = teams.map((team) => {
      console.log(team);
      let imageSrc = team.profileImgUri ? team.profileImgUri : Temp;
      return (
        <div key={team.teamName} className={styles.team_one_wrapper}>
          <img className={styles.team_image} src={imageSrc}></img>
          <span className={styles.team_name}>{team.teamName}</span>
        </div>
      );
    });
  }

  return <div className={styles.wrapper}>{teamList}</div>;
}
