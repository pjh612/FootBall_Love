import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { logoutAction } from "../../action/createAction";
import { useDispatch } from "react-redux";

const Logout = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(logoutAction());
    navigate("/");
  }, []);

  return <></>;
};

export default Logout;
