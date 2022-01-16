import Write from "./Write";
import CheckLogin from "../CheckLogin";

const WriteContainer = ({ user }) => {
  return (
    <CheckLogin user={user}>
      <Write></Write>
    </CheckLogin>
  );
};

export default WriteContainer;
