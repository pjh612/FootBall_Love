import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import React from "react";

const WriteAny = ({ user }) => {
  const writer = user.id;
  console.log(writer);

  const handleSubmit = (e) => {
    console.log(e.target.title.value); // 글제목
    console.log(e.target.content.value); // 글내용
    console.log(e.target.img.files); // 업로드파일
  };

  return (
    <Box
      component="form"
      onSubmit={handleSubmit}
      action="#"
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
      <input name="img" id="img" accept="image/*" type="file" multiple />
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
      <Button type="submit" variant="contained" sx={{ mt: 3 }}>
        작성하기
      </Button>
    </Box>
  );
};

export default WriteAny;
