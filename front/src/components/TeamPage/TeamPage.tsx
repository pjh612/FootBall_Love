import TeamPageImage from './TeamPageImage';
import SketchCanvas from './SketchCanvas';
import { useParams } from 'react-router-dom';
import { useTeam } from '../../hooks/useUser';
import { useNavigate } from 'react-router-dom';
import styles from '../../css/TeamPage.module.css';
import TeamPageInfo from './TeamPageInfo';
const TeamPage = () => {
  const id = useParams().id;
  const teams = useTeam();
  const navigate = useNavigate();
  const thisTeam = teams.filter((team) => team.teamId === Number(id));
  // if (thisTeam.length == 0) {
  //   console.log('존재하지 않는 팀입니다.');
  //   navigate('/');
  // }

  return (
    <div className={styles.wrapper}>
      <TeamPageImage teamUri={teams.profileImgUri}></TeamPageImage>
      <TeamPageInfo></TeamPageInfo>
      {/* <SketchCanvas></SketchCanvas> */}
    </div>
  );
};

export default TeamPage;
