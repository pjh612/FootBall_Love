import { imageListClasses } from '@mui/material';
import { immerable } from 'immer';

export default function WriteInput({ type, name }) {
  // type: image / string
  return (
    <>
      <div>
        <label htmlFor="inputBox">{name}</label>
        <input></input>
      </div>
    </>
  );
}
