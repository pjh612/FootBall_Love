import Button from './Button';

export default function BoardList({ boards, onDeleteBoard }) {
  const lists = [];
  for (let i = 0; i < boards.length; i++) {
    const board = boards[i];
    const listItem = (
      <li>
        {board.boardName}
        <Button event={() => onDeleteBoard(board.boardId)} text="삭제"></Button>
      </li>
    );
    lists.push(listItem);
  }
  return <ul>{lists}</ul>;
}
