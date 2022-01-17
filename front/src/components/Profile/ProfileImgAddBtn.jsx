import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
// import { useState } from "react";
import { postUserImg } from "../../axios/axios";

const ProfileImgAddBtn = () => {
  //   const [upLoadImg, setUpLoadImg] = useState(null);

  const onChange = (e) => {
    const newImg = e.target.files[0];
    const formData = new FormData();
    formData.append("file", newImg);
    postUserImg(formData)
      .then((res) => console.log(res))
      .catch((res) => console.log(res));
  };

  const Input = styled("input")({
    display: "none",
  });
  return (
    <label htmlFor="contained-button-file">
      <Input
        onChange={onChange}
        accept="image/*"
        id="contained-button-file"
        type="file"
      />
      <Button variant="contained" component="span">
        프로필사진 변경
      </Button>
    </label>
  );
};

export default ProfileImgAddBtn;
