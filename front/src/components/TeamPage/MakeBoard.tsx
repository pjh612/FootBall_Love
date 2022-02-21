import React from 'react';
import styles from '../../css/board.module.css';
import { makeBoardRequest } from '../../axios/axios';

export default function MakeBoard({ teamInfo }) {
  const boardTypes = ['NOTICE', 'PHOTO', 'GENERAL'];
  const onSubmit = () => {
    return (e: React.FormEvent) => {
      const boardName = e.target[0].value;
      const teamId = teamInfo.id;
      const boardType = boardTypes[0];
      const data = {
        boardName,
        teamId,
        boardType,
      };
      makeBoardRequest(teamId, data)
        .then(() => {
          console.log('보드 생성 완료');
        })
        .catch((err) => {
          console.log(err.response);
          console.log(err);
          console.log('보드생성실패');
        });

      console.log(e.target[0].value);
      e.preventDefault();
    };
  };
  return (
    <>
      <form onSubmit={onSubmit()}>
        <input placeholder="보드이름"></input>
        <button className={styles.submit_btn} type="submit">
          제출
        </button>
      </form>
    </>
  );
}
