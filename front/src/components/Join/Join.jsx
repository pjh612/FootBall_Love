import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {sendJoinData} from "../../axios/axios";
import SportsBasketballIcon from '@mui/icons-material/SportsBasketball';
import Avatar from '@mui/material/Avatar';
import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

const Join = () => {
    const [joinSuccess, setJoinSuccess] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
      if(joinSuccess) {
        navigate('/login');
      }
    }, [joinSuccess, navigate])

    const handleSubmit = (e) => {
        e.preventDefault();
        const data = { id :e.target.id.value,
                pwd : e.target.pwd.value,
                nickname: e.target.nickname.value,
                address: {
                    city: '서울',
                    street: e.target.address.value,
                    zipcode: '122'},
                name: e.target.name.value,
                birth: e.target.birth.value,
                email: e.target.email.value,
                phone: e.target.phone.value,
                type: 'NORMAL'
            }
        
        sendJoinData(data)
        .then((res) => {
            setJoinSuccess(true);
        })
        .catch((err) => {
            console.error(err);
        });
    }

    return(
        <Box sx={{display: "flex",
        justifyContents: "center",
        flexDirection: 'column',
        alignItems: 'center',
        width: "450px",
        paddingTop: "50px",
        paddingBottom: "50px",
        }}>
            <Avatar sx={{ m: 1, bgcolor: 'black' }}>
            <SportsBasketballIcon />
          </Avatar>
            <Typography  variant="h4" sx={{fontWeight:'bold'}}>
            풋볼러브 아이디 생성
          </Typography>
          <Typography  variant="h7" sx={{mt: 0.5 }}>
          주변 사람들과 즐기는 짜릿한 한판 승부, 풋볼러브에서!
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 , width: "450px"}}>
            <TextField id="name" label="이름" variant="outlined" fullWidth name="name"
                sx={{
                    marginTop: '30px',
                    display: 'block',
                }}
            />
             <TextField id="birth" label="생년월일 (YYYYMMDD)" variant="outlined" type="birth" name="birth" margin="normal"
              fullWidth
                sx={{
                    marginTop: '20px',
                    display: 'block',
                }}
            />
            <TextField id="id" label="아이디" variant="outlined" name="id" margin="normal"
              fullWidth
                sx={{
                    marginTop: '40px',
                    display: 'block',
                }}
            />
            <TextField id="pwd" label="비밀번호" variant="outlined" type="password" name="pwd" margin="normal"
              fullWidth
                sx={{
                    marginTop: '20px',
                    display: 'block',
                }}
            />
            <TextField id="nickname" label="닉네임" variant="outlined" name="nickname" margin="normal"
              fullWidth
                sx={{
                    marginTop: '20px',
                    display: 'block',
                }}
            />
            <TextField id="phone" label="휴대폰번호" variant="outlined" name="phone" margin="normal"
              fullWidth
                sx={{
                    marginTop: '40px',
                    display: 'block',
                }}
            />
            <TextField id="email" label="이메일" variant="outlined" name="email" margin="normal"
              fullWidth
                sx={{
                    marginTop: '20px',
                    display: 'block',
                }}
            />
            <TextField id="address" label="주소" variant="outlined" name="address" margin="normal"
              fullWidth
                sx={{
                    marginTop: '20px',
                    display: 'block',
                }}
            />
             
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              가입하기
            </Button>
            </Box>
        </Box>
    )
}

export default Join;