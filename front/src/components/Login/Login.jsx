import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Link';
import {sendLoginData} from "../../axios/axios";
import SportsBasketballIcon from '@mui/icons-material/SportsBasketball';
import Avatar from '@mui/material/Avatar';

const Login = () => {
    const handleSubmit = (e) => {
        e.preventDefault();
        const data = { id :e.target.id.value,
                pwd : e.target.password.value}
        sendLoginData(data)
        .then((res) => {
            console.log(res);
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
        paddingTop: "100px",
        }}>
            <Avatar sx={{ m: 1, bgcolor: 'black' }}>
            <SportsBasketballIcon />
          </Avatar>
            <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 , width: "450px"}}>
            <TextField id="id" label="아이디" variant="outlined" fullWidth name="id"
                sx={{
                    marginTop: '30px',
                    display: 'block',
                }}
            />
             <TextField id="password" label="비밀번호" variant="outlined" type="password" name="password" margin="normal"
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
              로그인
            </Button>
            </Box>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2" >
                  비밀번호를 잃어버렸습니까?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/join" variant="body2">
                  {"계정이 없으십니까? 회원가입하기"}
                </Link>
              </Grid>
            </Grid>
        </Box>
    );
}

export default Login;