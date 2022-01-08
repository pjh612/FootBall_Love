import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import CheckLogin from "../CheckLogin";

const Write = ({ user }) => {
  return (
    <CheckLogin>
      <Box
        sx={{
          width: 500,
          maxWidth: "100%",
        }}
      >
        <TextField fullWidth label="fullWidth" id="fullWidth" />
      </Box>
    </CheckLogin>
  );
};

export default Write;
