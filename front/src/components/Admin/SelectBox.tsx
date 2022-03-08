export default function SelectBox({ onSelectBoardType, selectList }) {
  return (
    <select onChange={onSelectBoardType}>
      {selectList.map((item) => (
        <option value={item} key={item}>
          {item}
        </option>
      ))}
    </select>
  );
}
