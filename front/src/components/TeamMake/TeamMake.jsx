import Box from "@mui/material/Box";
import TeamInput from "./TeamMakeInput";

const TeamMake = ({ user }) => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <TeamInput user={user}></TeamInput>
    </Box>
  );
};

export default TeamMake;
