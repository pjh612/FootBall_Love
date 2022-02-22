import styles from '../../css/TeamList.module.css';
import Temp from '../../asset/team.svg';
import { useTeam } from '../../hooks/useUser';
import { useNavigate } from 'react-router-dom';
import React from 'react';

type ClickHandler = (teamId: number) => (e: React.MouseEvent) => void;

export default function TeamList() {
  const teams = useTeam();
  const navigate = useNavigate();
  let teamList = null;

  const routeToTeamPage: ClickHandler = (teamId: number) => (e) => {
    navigate(`/teams/${teamId}`);
  };

  if (teams.length === 0) {
    console.log('팀이 아직 없습니다.');
  } else {
    teamList = teams.map((team) => {
      console.log(team);
      const imageSrc = team.profileImgUri ? team.profileImgUri : Temp;
      return (
        <div onClick={routeToTeamPage(team.teamId)} key={team.teamName} className={styles.team_one_wrapper}>
          <img className={styles.team_image} src={imageSrc}></img>
          <span className={styles.team_name}>{team.teamName}</span>
        </div>
      );
    });
  }

  return <div className={styles.wrapper}>{teamList}</div>;
}
