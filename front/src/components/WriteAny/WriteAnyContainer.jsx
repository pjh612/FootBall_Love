import WriteAny from "./WriteAny";
import CheckLogin from "../CheckLogin";

const WriteAnyContainer = ({ user }) => {
  return (
    <CheckLogin user={user}>
      <WriteAny></WriteAny>
    </CheckLogin>
  );
};

export default WriteAnyContainer;
