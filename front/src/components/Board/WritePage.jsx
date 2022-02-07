import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import React from "react";
import { postUserPost } from "../../axios/axios";
import { useSelector } from "react-redux";

const Write = () => {
  const user = useSelector((state) => state.userReducer.user);

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();
    const content = e.target.content.value;
    const title = e.target.content.value;
    const authorNumber = user.number;
    const teamId = user.teams[0].teamId;
    const images = e.target.img.files.file;

    formData.append("content", content);
    formData.append("title", title);
    formData.append("authorNumber", authorNumber);
    formData.append("boardId", 7);
    formData.append("teamId", teamId);
    for (let i = 0; i < e.target.img.files; i++) {
      formData.append("images[" + i + "]", images[i]);
    }
    postUserPost(formData)
      .then((res) => console.log(res))
      .catch((err) => console.log(err));

    console.log(content);
    console.log(title);
    console.log(authorNumber);
    console.log(teamId);
    console.log(images);
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

export default Write;
