import BoardNameInput from './BoardNameInput';
import CategoryName from './CategoryName';
import SelectBox from './SelectBox';
import Button from './Button';
import BoardList from './BoardList';
import { useState, useEffect } from 'react';
import { makeBoardAdmin, getBoardList, deleteBoard } from '../../axios/axios';

export default function Board() {
  const selectList = ['NOTICE', 'PHOTO', 'GENERAL'];
  const [newBoard, setNewBoard] = useState({
    boardName: '',
    boardType: 'NOTICE', //defalut boardType
  });
  const [boards, setBoards] = useState([]);
  const [trigger, setTrigger] = useState(0); // when new board added or deleted, trigger work.

  useEffect(() => {
    getBoardList()
      .then((res) => setBoards(res.data))
      .catch((err) => console.log(err));
  }, [trigger]);

  const onSelectBoardType = (e) => {
    const boardType = e.target.value;
    setNewBoard({
      ...newBoard,
      boardType,
    });
  };

  const onChangeBoardName = (e) => {
    const boardName = e.target.value;
    setNewBoard({
      ...newBoard,
      boardName,
    });
  };

  const onSubmitBoardName = () => {
    makeBoardAdmin(newBoard)
      .then((res) => {
        console.log(res);
        setTrigger(trigger + 1);
      })
      .catch((err) => {
        console.log('보드 생성 에러');
        console.log(err);
      });
  };

  const onDeleteBoard = (boardId) => {
    deleteBoard(boardId)
      .then((res) => {
        console.log(res);
        setTrigger(trigger + 1);
      })
      .catch((err) => {
        console.log(err);
        console.log('[error]보드 삭제 실패');
      });
  };

  const boardName = newBoard.boardName;

  return (
    <>
      <CategoryName category={'보드 생성'}></CategoryName>
      <p>
        <SelectBox onSelectBoardType={onSelectBoardType} selectList={selectList}></SelectBox>
        <BoardNameInput boardName={boardName} onChangeBoardName={onChangeBoardName}></BoardNameInput>
        <Button event={onSubmitBoardName} text={'생성하기'}></Button>
      </p>
      <CategoryName category={'보드 리스트'}></CategoryName>
      <BoardList onDeleteBoard={onDeleteBoard} boards={boards}></BoardList>
    </>
  );
}
