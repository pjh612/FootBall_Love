export default function CategoryHeader({ category }) {
  const categoryLists = category.map((name) => {
    return <li key={name}>{name}</li>;
  });

  return (
    <>
      <div>
        <ul>{categoryLists}</ul>
      </div>
    </>
  );
}
