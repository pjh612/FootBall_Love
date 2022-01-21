import CheckLogin from "../CheckLogin";
import Profile from "./Profile";

const ProfileContainer = ({ user }) => {
  return (
    <CheckLogin user={user}>
      <Profile></Profile>
    </CheckLogin>
  );
};

export default ProfileContainer;
