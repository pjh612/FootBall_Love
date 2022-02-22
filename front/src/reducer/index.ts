import { combineReducers } from 'redux';
import { userReducer } from './reducer';

const configurestore = combineReducers({
  userReducer,
});

export type RootState = ReturnType<typeof configurestore>;

export default configurestore;
