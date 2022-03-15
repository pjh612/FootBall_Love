import TeamPageImage from './TeamPageImage';
import { useParams } from 'react-router-dom';
import { useTeam } from '../../hooks/useUser';
import { useNavigate } from 'react-router-dom';
import styles from '../../css/TeamPage.module.css';
// import TeamPageInfo from './TeamPageInfo';
import { getDetailTeamInfo } from '../../axios/axios';
import CheckLogin from '../CheckLogin';
import MakeBoard from './MakeBoard';
import TeamBoardList from './TeamBoardList';

import { useState, useEffect, Suspense, lazy } from 'react';

const TeamInfo = lazy(() => import('./TeamPageInfo'));

interface Board {
  boardId: number;
  boardName: string;
  boardType: string;
  teamId: number;
}

type teamInfo = {
  name: string | null;
  lastModifiedBy: string | null;
  lastModifiedDate: string | null;
  id: number | null;
  info: string | null;
  introduce: string | null;
  profileImgUri: string | null;
  teamMember: string[] | null;
  boards: Board[];
};

const TeamPage = () => {
  const [teamInfo, setTeamInfo] = useState<teamInfo>({
    name: null,
    lastModifiedBy: null,
    lastModifiedDate: null,
    profileImgUri: null,
    introduce: null,
    teamMember: [],
    info: null,
    id: null,
    boards: [],
  });

  const teamId = useParams().id;
  const teams = useTeam();
  const navigate = useNavigate();
  const nowTeam = teams.filter((team) => {
    return team.teamId == teamId;
  });

  async function getDetailInfo(teamId) {
    try {
      const teamData: any = await getDetailTeamInfo(teamId).then((res) => res.data);
      setTeamInfo(teamData);
    } catch (err) {
      console.log('팀 정보를 가져오는데 실패했습니다');
      console.log(err.response);
    }
  }

  useEffect(() => {
    getDetailInfo(teamId);
  }, []);

  return (
    <CheckLogin>
      <div className={styles.wrapper}>
        {/* <TeamPageImage teamUri={teams.profileImgUri}></TeamPageImage> */}
        <Suspense fallback={<h1>로딩중이야아아아아아아</h1>}>
          <TeamInfo teamInfo={teamInfo}></TeamInfo>
        </Suspense>
        {/* <MakeBoard teamInfo={teamInfo}></MakeBoard> */}
        {/* <TeamBoardList teamInfo={teamInfo}></TeamBoardList> */}
      </div>
    </CheckLogin>
  );
};

export default TeamPage;
