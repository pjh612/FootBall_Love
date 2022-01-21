import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import React from "react";

const WriteMatch = ({ user }) => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      name: user.name,
      id: user.id,
    };
    return data;
  };
  const [location, setLocation] = React.useState("");

  const handleChange = (event) => {
    setLocation(event.target.value);
  };

  const locals = [
    "서울",
    "경기",
    "인천",
    "대전",
    "충북",
    "충남",
    "대구",
    "부산",
    "울산",
    "경남",
    "경북",
    "광주",
    "전북",
    "강원",
    "제주",
    "전남",
  ];

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
      <FormControl sx={{ m: 1, minWidth: 120 }}>
        <InputLabel id="demo-simple-select-helper-label">지역</InputLabel>
        <Select
          labelId="demo-simple-select-helper-label"
          id="demo-simple-select-helper"
          value={location}
          label="location"
          onChange={handleChange}
        >
          {locals.map((local, idx) => {
            return (
              <MenuItem key={idx} value={local}>
                {local}
              </MenuItem>
            );
          })}
        </Select>
      </FormControl>
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

export default WriteMatch;
