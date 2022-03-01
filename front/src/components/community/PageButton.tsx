export default function PageButton({ pageInfo }) {
  // if pageInfo.maxNumber == pageInfo.number -> next inactive
  return (
    <div>
      <p>
        <button>Before</button>
        <button>{pageInfo.number}</button>
        <button>Next</button>
      </p>
    </div>
  );
}
