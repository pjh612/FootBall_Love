export default function SelectBox({ selectList }) {
  return (
    <select>
      {selectList.map((item) => (
        <option value={item} key={item}>
          {item}
        </option>
      ))}
    </select>
  );
}
