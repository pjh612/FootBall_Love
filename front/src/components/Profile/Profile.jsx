import ProfileAvatar from "./ProfileAvatar";
import ProfileInfo from "./ProfileInfo";
import styled from "styled-components";

const FlexBox = styled.div`
  display: flex;
  justify-content: center;
`;

const AvatarWrapper = styled.div`
  width: 300px;
  height: 100%;
`;

const InfoWrapper = styled.div`
  width: 500px;
  height: 100%;
`;

const Profile = () => {
  return (
    <>
      <FlexBox>
        <AvatarWrapper>
          <ProfileAvatar></ProfileAvatar>
        </AvatarWrapper>
        <InfoWrapper>
          <ProfileInfo></ProfileInfo>
        </InfoWrapper>
      </FlexBox>
    </>
  );
};

export default Profile;
