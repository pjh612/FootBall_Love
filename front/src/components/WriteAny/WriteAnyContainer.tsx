import WriteAny from './WriteAny';
import CheckLogin from '../CheckLogin';

const WriteAnyContainer = () => {
  return (
    <CheckLogin>
      <WriteAny></WriteAny>
    </CheckLogin>
  );
};

export default WriteAnyContainer;
