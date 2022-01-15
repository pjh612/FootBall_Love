import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
// 팀 아바타와 팀명

const TeamPageHeader = () => {
  return (
    <Box
      sx={{
        height: 145,
        display: "flex",
        borderBottom: "1px solid black",
      }}
    >
      <Avatar
        alt="팀이미지"
        src="img/훌쩍1.png"
        sx={{
          width: "120px",
          height: "120px",
          border: "1px solid black",
          borderRadius: "30px",
          ml: "210px",
          mt: "16px",
        }}
        variant="square"
      />
      <Typography variant="h4" sx={{ mt: "51.2px", ml: "20px" }}>
        성북구 메시들
      </Typography>
    </Box>
  );
};

export default TeamPageHeader;
