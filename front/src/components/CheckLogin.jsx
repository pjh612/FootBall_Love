import React from "react";

const CheckLogin = ({user, children}) => {
    if (user) {
        return React.cloneElement(children, {
          user: user
        })
    }
    return <p>로그인 후 진행하실 수 있습니다.</p>
}

export default CheckLogin;