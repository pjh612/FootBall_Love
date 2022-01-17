import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  status: {
    danger: "#e53e3e",
  },
  palette: {
    primary: {
      main: "#0971f1",
      darker: "#053e85",
    },
    neutral: {
      main: "#64748B",
      contrastText: "#fff",
    },
    wblack: {
      main: "#616161",
    },
  },
});

export default function TeamPageNav() {
  return (
    <ThemeProvider theme={theme}>
      <Box
        sx={{
          display: "flex",

          paddingLeft: "210px",
          borderBottom: "1px solid #eeeeee",
        }}
      >
        <Button color="wblack">팀 정보</Button>
        <Button color="wblack">팀 정보</Button>
        <Button color="wblack">팀 정보</Button>
        <Button color="wblack">팀 정보</Button>
        <Button color="wblack">팀 정보</Button>
        <Button color="wblack">팀 정보</Button>
      </Box>
    </ThemeProvider>
  );
}
