import Box from "@mui/material/Box";
import TeamInput from "./TeamMakeInput";
import CheckLogin from "../CheckLogin";

const TeamMake = () => {
  return (
    <CheckLogin>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <TeamInput></TeamInput>
      </Box>
    </CheckLogin>
  );
};

export default TeamMake;
