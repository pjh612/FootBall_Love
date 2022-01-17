import Box from "@mui/material/Box";
import TeamPageHeader from "./TeamPageHeader";
import TeamPageNav from "./TeamPageNav";
import SketchCanvas from "./SketchCanvas";
const TeamPage = () => {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
      }}
    >
      <TeamPageHeader></TeamPageHeader>
      <TeamPageNav></TeamPageNav>
      <SketchCanvas></SketchCanvas>
    </Box>
  );
};

export default TeamPage;
