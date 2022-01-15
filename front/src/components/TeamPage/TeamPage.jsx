import Box from "@mui/material/Box";
import TeamPageHeader from "./TeamPageHeader";

const TeamPage = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
      }}
    >
      <TeamPageHeader></TeamPageHeader>
    </Box>
  );
};

export default TeamPage;
