import styles from '../../css/TeamList.module.css';
import Temp from '../../asset/team.svg';
import { useTeam } from '../../hooks/useUser';
import { useNavigate } from 'react-router-dom';
import React from 'react';
import TeamCard from './TeamCard';

type ClickHandler = (teamId: number) => (e: React.MouseEvent) => void;

export default function TeamList() {
  const teams = useTeam();
  const navigate = useNavigate();
  let teamList = null;

  const routeToTeamPage: ClickHandler = (teamId: number) => () => {
    navigate(`/teams/${teamId}`);
  };

  if (teams.length === 0) {
    console.log('팀이 아직 없습니다.');
  } else {
    teamList = teams.map((team) => {
      console.log(team);
      const imageSrc = team.profileImgUri ? team.profileImgUri : Temp;
      return (
        <TeamCard
          link={routeToTeamPage(team.teamId)}
          name={team.teamName}
          key={team.teamName}
          imageSrc={imageSrc}
        ></TeamCard>
      );
    });
  }

  return (
    <div className={styles.background}>
      <div className={styles.wrapper}>{teamList}</div>
    </div>
  );
}
