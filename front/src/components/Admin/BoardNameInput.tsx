export default function BoardNameInput({ boardName, onChangeBoardName }) {
  return (
    <>
      <input onChange={onChangeBoardName} value={boardName} placeholder="typing boardName"></input>
    </>
  );
}
