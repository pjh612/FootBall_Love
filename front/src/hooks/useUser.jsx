import { useSelector } from "react-redux";

export const useUser = () => {
  const user = useSelector((state) => state.userReducer.user);
  return user;
};

export const useTeam = () => {
  const teams = useSelector((state) => state.userReducer.user.teams);
  return teams;
};
