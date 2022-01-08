import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
const ProfileInfo = ({user}) => {
    // defaultValue 를 user 의 속성으로 만들어주면됨
    const userName = user.name;
    const userNickName = user.nickname;
    const userBirth = user.birth;
    const userEmail = user.email;

    return(
        <Stack ml={3} mt={8} spacing={3}>
        <TextField disabled fullWidth id="standard-basic" defaultValue={userName} label="이름" variant="standard" />
        <TextField fullWidth id="standard-basic" defaultValue={userNickName} label="닉네임" variant="standard" />
        <TextField fullWidth id="standard-basic" defaultValue="왼쪽 윙" label="포지션" variant="standard" />
        <TextField fullWidth id="standard-basic" defaultValue="서울시 성북구" label="지역" variant="standard" />
        <TextField disabled fullWidth id="standard-basic" defaultValue={userBirth} label="생년월일" variant="standard" />
        <TextField disabled fullWidth id="standard-basic" defaultValue={userEmail} label="이메일" variant="standard" />
        <Button variant="outlined">변경사항 저장하기</Button>
        </Stack>
    )
}

export default ProfileInfo;