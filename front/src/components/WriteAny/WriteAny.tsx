import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import React from 'react';
import { postUserPost } from '../../axios/axios';
import { useUser } from '../../hooks/useUser';

const WriteAny = () => {
  const user = useUser();
  const writer = user.id;

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('content', e.target.content.value);
    formData.append('title', e.target.content.value);
    formData.append('authorId', user.number);
    formData.append('boardId', '1');
    formData.append('teamId', user.teams[0].teamId);
    for (let i = 0; i < e.target.img.files; i++) {
      formData.append('images[' + i + ']', e.target.img.files.file[i]);
    }
    postUserPost(formData);
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
        maxWidth: '100%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        margin: 'auto',
        border: '1px solid black',
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
          marginTop: '20px',
          display: 'block',
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
          marginTop: '20px',
          display: 'block',
        }}
      />
      <Button type="submit" variant="contained" sx={{ mt: 3 }}>
        작성하기
      </Button>
    </Box>
  );
};

export default WriteAny;
