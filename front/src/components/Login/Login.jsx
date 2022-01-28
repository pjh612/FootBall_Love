import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";
import Link from "@mui/material/Link";
import { getUserInfo, sendLoginData } from "../../axios/axios";
import SportsBasketballIcon from "@mui/icons-material/SportsBasketball";
import Avatar from "@mui/material/Avatar";
import { updateUserAction } from "../../action/createAction";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { Div, Div2 } from "./styled";

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    const data = { id: e.target.id.value, pwd: e.target.password.value };
    try {
      const loginKey = await sendLoginData(data).then(
        (res) => res.data.memberNumber
      );
      const userInfo = await getUserInfo(loginKey).then((res) => res.data[0]);
      const action = updateUserAction(userInfo);
      dispatch(action);
      navigate("/");
    } catch (err) {
      console.log("error!");
      console.log(err);
    }
  }

  return (
    <Div>
      <Div2>
        <Box
          sx={{
            display: "flex",
            justifyContents: "center",
            flexDirection: "column",
            alignItems: "center",
            width: "450px",
            paddingTop: "100px",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "black", width: 50, height: 50 }}>
            <SportsBasketballIcon sx={{ width: 37, height: 37 }} />
          </Avatar>
          <Typography component="h1" variant="h4">
            로그인하기
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1, width: "450px" }}
          >
            <TextField
              id="id"
              label="아이디"
              variant="outlined"
              fullWidth
              name="id"
              sx={{
                marginTop: "30px",
                display: "block",
              }}
            />
            <TextField
              id="password"
              label="비밀번호"
              variant="outlined"
              type="password"
              name="password"
              margin="normal"
              fullWidth
              sx={{
                marginTop: "20px",
                display: "block",
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
              <Link href="#" variant="body2">
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
      </Div2>
    </Div>
  );
};

export default Login;
