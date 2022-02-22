import { useSelector } from 'react-redux';
import { RootState } from '../reducer/index';

export const useUser = () => {
  const user = useSelector((state: RootState) => state.userReducer.user);
  return user;
};

export const useTeam = () => {
  const teams = useSelector((state: RootState) => state.userReducer.user.teams);
  return teams;
};
