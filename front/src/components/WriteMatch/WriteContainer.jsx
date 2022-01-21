import WriteMatch from "./WriteMatch";
import CheckLogin from "../CheckLogin";

const WriteMatchContainer = ({ user }) => {
  return (
    <CheckLogin user={user}>
      <WriteMatch></WriteMatch>
    </CheckLogin>
  );
};

export default WriteMatchContainer;
