import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import { postUserImg } from "../../axios/axios";
import { useDispatch } from "react-redux";
import { updateUserProfileUri } from "../../action/createAction";

const ProfileImgAddBtn = () => {
  //   const [upLoadImg, setUpLoadImg] = useState(null);
  const dispatch = useDispatch();

  const onChange = async (e) => {
    const newImg = e.target.files[0];
    const formData = new FormData();
    formData.append("file", newImg);

    try {
      const profileUri = await postUserImg(formData).then((res) => res.data);
      dispatch(updateUserProfileUri(profileUri));
    } catch (err) {
      console.log(err);
    }
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
