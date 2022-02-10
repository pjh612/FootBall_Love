import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { getUserInfo, sendLoginData, getTeamInfo } from "../../axios/axios";
import SportsBasketballIcon from "@mui/icons-material/SportsBasketball";
import Avatar from "@mui/material/Avatar";
import { updateUserAction, updateTeamAction } from "../../action/createAction";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { Div, Div2 } from "./styled";

import styles from "../../css/Login.module.css";
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
      console.log(loginKey);
      const userInfo = await getUserInfo().then((res) => res.data);
      const teamInfo = await getTeamInfo().then((res) => res.data);
      const useraction = updateUserAction(userInfo);
      const teamaction = updateTeamAction(teamInfo);
      dispatch(useraction);
      dispatch(teamaction);
      navigate("/");
    } catch (err) {
      console.log("로그인 에러 [ID, 비밀번호 정보가 올바르지 않습니다.]");
      console.log(err.response);
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
            width: "100%",
            paddingTop: "100px",
            background: "#FFEEAD",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "black", width: 40, height: 40 }}>
            <SportsBasketballIcon sx={{ width: 25, height: 25 }} />
          </Avatar>
          <Typography variant="h4" sx={{ fontWeight: "bold", mt: 1 }}>
            로그인하기
          </Typography>
          <Typography variant="h7" sx={{ mt: 0.5 }}>
            풋볼러브에서 축구 한판승부!
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
              color="dark"
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
              color="dark"
              backgroundColor="dark"
              fullWidth
              sx={{
                marginTop: "20px",
                display: "block",
              }}
            />
            <Button
              type="submit"
              fullWidth
              variant="outlined"
              color="dark"
              fontSize="large"
              sx={{ mt: 3, mb: 2 }}
            >
              로그인
            </Button>
          </Box>
          <div>
            <span
              onClick={() => navigate("/join")}
              className={styles.joinMessage}
            >
              회원가입 하러가기
            </span>
          </div>
        </Box>
      </Div2>
    </Div>
  );
};

export default Login;
