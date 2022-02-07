import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { logoutAction } from "../../action/createAction";
import { useDispatch } from "react-redux";
import { logoutRequest } from "../../axios/axios";
const Logout = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    logoutRequest()
      .then(() => {
        dispatch(logoutAction());
        navigate("/");
      })
      .catch((err) => console.log(err));
  }, []);

  return <></>;
};

export default Logout;
