import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";

const Write = ({ user }) => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      name: user.name,
      id: user.id,
    };
    return data;
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      sx={{
        width: 800,
        maxWidth: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        margin: "auto",
        border: "1px solid black",
        padding: 2,
        mt: 3,
        pb: 5,
      }}
    >
      <TextField
        id="title"
        label="제목"
        variant="outlined"
        name="title"
        margin="normal"
        fullWidth
        sx={{
          marginTop: "20px",
          display: "block",
        }}
      />
      <TextField
        id="content"
        label="내용"
        variant="outlined"
        name="content"
        margin="normal"
        multiline
        rows={10}
        fullWidth
        sx={{
          marginTop: "20px",
          display: "block",
        }}
      />
    </Box>
  );
};

export default Write;
