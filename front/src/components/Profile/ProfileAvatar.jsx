import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import ProfileImgAddBtn from "./ProfileImgAddBtn";
import { useSelector } from "react-redux";

const ProfileAvatar = () => {
  const user = useSelector((state) => state.userReducer.user);

  return (
    <Stack mt={10} spacing={2} alignItems="center">
      <Avatar
        alt="Remy Sharp"
        src={
          user.profileUri
            ? `https://storage.googleapis.com/fbl_profile_img/${user.profileUri}`
            : "#"
        }
        sx={{
          width: "256px",
          height: "256px",
          borderColor: "black",
          border: 1,
        }}
      />
      <ProfileImgAddBtn></ProfileImgAddBtn>
    </Stack>
  );
};

export default ProfileAvatar;
