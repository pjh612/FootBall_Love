import React from "react";
import LoginPlz from "./ErrorPage/LoginPlz";

const CheckLogin = ({ user, children }) => {
  if (!user) {
    return <LoginPlz></LoginPlz>;
  }
  if (user) {
    return React.cloneElement(children, {
      user: user,
    });
  }
};

export default CheckLogin;
