import CheckLogin from "../CheckLogin";
import Profile from "./Profile";

const ProfileContainer = () => {
  return (
    <CheckLogin>
      <Profile></Profile>
    </CheckLogin>
  );
};

export default ProfileContainer;
