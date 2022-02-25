import { useUser } from '../../hooks/useUser';
import BoardList from './BoardList';
import BoardNameInput from './BoardNameInput';
import CategoryName from './CategoryName';
import SelectBox from './SelectBox';
import Button from './Button';
export default function AdminPage({ user }) {
  // if (user.type !== 'ROLE_ADMIN') {
  //   return <h1>This page is accessable only admin.</h1>;
  // }
  const selectList = ['NOTICE', 'PHOTO', 'GENERAL'];
  return (
    <>
      <CategoryName category={'보드 생성'}></CategoryName>
      <p>
        <SelectBox selectList={selectList}></SelectBox>
        <BoardNameInput></BoardNameInput>
        <Button text={'생성하기'}></Button>
      </p>
      <CategoryName category={'보드 리스트'}></CategoryName>
      <BoardList></BoardList>
    </>
  );
}
