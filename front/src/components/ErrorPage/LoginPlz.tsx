import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Stack from "@mui/material/Stack";
import { useNavigate } from "react-router-dom";

const LoginPlz = () => {
  const navigate = useNavigate();
  const navToLogin = () => {
    navigate("/login");
  };
  const navToJoin = () => {
    navigate("/join");
  };

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 1000,
        margin: "auto",
        marginTop: 18,
        display: "flex",
        justifyContent: "center",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Typography variant="h3" gutterBottom component="div">
        로그인 후 이용할 수 있습니다.
      </Typography>
      <Typography variant="h5" gutterBottom component="div">
        축구는 풋볼러브!
      </Typography>
      <Stack direction="row" spacing={2}>
        <Button onClick={navToLogin} variant="outlined">
          로그인하기
        </Button>
        <Button onClick={navToJoin} variant="outlined">
          회원가입하기
        </Button>
      </Stack>
    </Box>
  );
};

export default LoginPlz;
