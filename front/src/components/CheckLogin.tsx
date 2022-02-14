import React from 'react';
import LoginPlz from './ErrorPage/LoginPlz';
import { useUser } from '../hooks/useUser';

type CheckedElem = {
  children: JSX.Element;
};

const CheckLogin = ({ children }: CheckedElem) => {
  const user = useUser();

  if (user.id === '') {
    return <LoginPlz></LoginPlz>;
  } else {
    return React.cloneElement(children);
  }
};

export default CheckLogin;
