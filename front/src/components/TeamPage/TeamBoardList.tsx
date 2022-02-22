import { useNavigate } from 'react-router-dom';

export default function TeamBoardList({ teamInfo }) {
  const BoardList = [];
  const navigate = useNavigate();

  for (let i = 0; i < teamInfo.boards.length; i++) {
    BoardList.push(<p onClick={() => navigate(`./${teamInfo.boards[i].boardId}`)}>{teamInfo.boards[i].boardName}</p>);
  }
  return <>{BoardList}</>;
}
