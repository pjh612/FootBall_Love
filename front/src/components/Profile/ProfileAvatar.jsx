import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import ProfileImgAddBtn from "./ProfileImgAddBtn";

const ProfileAvatar = () => {
  //src 를 userImg 로 바꾸면 됨.
  return (
    <Stack mt={10} spacing={2} alignItems="center">
      <Avatar
        alt="Remy Sharp"
        src="img/훌쩍1.png"
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
