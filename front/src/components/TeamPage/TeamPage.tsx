import TeamPageImage from './TeamPageImage';
import SketchCanvas from './SketchCanvas';
import { useParams } from 'react-router-dom';
import { useTeam, useUser } from '../../hooks/useUser';
import { useNavigate } from 'react-router-dom';
import styles from '../../css/TeamPage.module.css';
import TeamPageInfo from './TeamPageInfo';
import { getDetailTeamInfo } from '../../axios/axios';
import CheckLogin from '../CheckLogin';
const TeamPage = () => {
  const id = useParams().id;
  const teams = useTeam();
  let teamInfo;

  const nowTeam = teams.filter((team) => {
    console.log(team);
    return team.teamId == id;
  });

  async function getDetailInfo(teamId) {
    try {
      const detailData = await getDetailTeamInfo(teamId);
      teamInfo = detailData.data;
      console.log(teamInfo);
    } catch (err) {
      console.log('팀 정보를 가져오는데 실패했습니다');
      console.log(err.response);
    }
  }

  if (nowTeam.length !== 0) {
    getDetailInfo(id);
  }

  return (
    <CheckLogin>
      <div className={styles.wrapper}>
        <TeamPageImage teamUri={teams.profileImgUri}></TeamPageImage>
        <TeamPageInfo></TeamPageInfo>
        {/* <SketchCanvas></SketchCanvas> */}
      </div>
    </CheckLogin>
  );
};

export default TeamPage;
