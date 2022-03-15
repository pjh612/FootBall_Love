import React from 'react';
import LoginPlz from './ErrorPage/LoginPlz';
import { useLogin } from '../hooks/useUser';

type CheckedElem = {
  children: JSX.Element;
};

const CheckLogin = ({ children }: CheckedElem) => {
  const isLogin = useLogin();

  if (!isLogin) {
    return <LoginPlz></LoginPlz>;
  } else {
    return React.cloneElement(children);
  }
};

export default CheckLogin;
