import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';

const ProfileAvatar = () => {
    //src 를 userImg 로 바꾸면 됨.
    return(
        <Stack mt={10} spacing={2} alignItems="center">
            <Avatar alt="Remy Sharp" src="img/훌쩍1.png" sx={{width: '256px', height: '256px', borderColor:'black', border:1}}/>
            <Button variant="outlined">프로필사진 변경</Button>
        </Stack>
    )
}

export default ProfileAvatar;