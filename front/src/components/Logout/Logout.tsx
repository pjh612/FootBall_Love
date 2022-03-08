import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { logoutAction } from '../../action/createAction';
import { useDispatch } from 'react-redux';
import { logoutRequest } from '../../axios/axios';
import { deleteCookie } from '../../utils';
const Logout = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    logoutRequest()
      .then(() => {
        deleteCookie('refreshToken');
        deleteCookie('accessToken');
        dispatch(logoutAction());
        navigate('/');
      })
      .catch((err) => {
        console.log('로그아웃 요청 에러');
        console.log(err.response);
        console.log(err);
      });
  }, []);

  return <></>;
};

export default Logout;
