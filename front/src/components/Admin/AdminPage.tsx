import Board from './Board';

export default function AdminPage({ user }) {
  // if (user.type !== 'ROLE_ADMIN') {
  //   return <h1>This page is accessable only admin.</h1>;
  // }

  return (
    <>
      <Board></Board>
    </>
  );
}
