import React from "react";
import LoginPlz from "./ErrorPage/LoginPlz";
import { useSelector } from "react-redux";

const CheckLogin = ({ children }) => {
  const user = useSelector((state) => state.userReducer.user);

  if (!user) {
    return <LoginPlz></LoginPlz>;
  }
  if (user) {
    return React.cloneElement(children);
  }
};

export default CheckLogin;
