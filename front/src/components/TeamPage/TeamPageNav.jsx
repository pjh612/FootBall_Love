import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import { createTheme, ThemeProvider } from "@mui/material/styles";

const theme = createTheme({
  palette: {
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
          width: "80vw",
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
