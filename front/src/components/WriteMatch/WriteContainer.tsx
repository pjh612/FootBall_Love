import WriteMatch from './WriteMatch';
import CheckLogin from '../CheckLogin';

const WriteMatchContainer = () => {
  return (
    <CheckLogin>
      <WriteMatch></WriteMatch>
    </CheckLogin>
  );
};

export default WriteMatchContainer;
