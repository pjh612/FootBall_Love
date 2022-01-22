import { combineReducers } from "redux";
import { userReducer } from "./reducer";

const configurestore = combineReducers({
  userReducer,
});

export default configurestore;
