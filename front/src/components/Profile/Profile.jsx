import ProfileAvatar from "./ProfileAvatar";
import ProfileInfo from "./ProfileInfo";
import CheckLogin from "../CheckLogin";
import { FlexBox, AvatarWrapper, InfoWrapper } from "./styled";

const Profile = () => {
  return (
    <>
      <CheckLogin>
        <FlexBox>
          <AvatarWrapper>
            <ProfileAvatar></ProfileAvatar>
          </AvatarWrapper>
          <InfoWrapper>
            <ProfileInfo></ProfileInfo>
          </InfoWrapper>
        </FlexBox>
      </CheckLogin>
    </>
  );
};

export default Profile;
