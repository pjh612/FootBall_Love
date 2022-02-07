import { useSelector } from "react-redux";

const useUser = () => {
  const user = useSelector((state) => state.userReducer.user);
  return user;
};

export default useUser;
