import React from "react";

const CheckLogin = ({user, children}) => {
  
    if (!user) { 
      return <p>로그인 후 진행하실 수 있습니다.</p>;
    }
    if (user) {
      return React.cloneElement(children, {
        user: user
      })
  }
}

export default CheckLogin;