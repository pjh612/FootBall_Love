import WriteButton from './WriteButton';
import CategoryHeader from './CategoryHeader';
import PostWrapper from './PostWrapper';
import PageButton from './PageButton';
import { useState, useEffect } from 'react';
import { getBoardList, getPosts } from '../../axios/axios';

export default function Community() {
  const [community, setCommunity] = useState({
    boards: [],
    selectedBoard: {
      boardName: '',
      boardId: null,
    },
    page: {
      nowPage: 1,
      firstPage: 1,
      lastPage: 10,
    },
    posts: [
      { title: '새로운 곳', user: 'ak123', time: '2021-01' },
      { title: '새로운 곳', user: 'ak123', time: '2021-01' },
    ],
  });
  const fetchCommunity = async () => {
    try {
      const boards = await getBoardList().then((res) => res.data);
      // default value is boards[0].
      const boardName = boards.length === 0 ? '' : boards[0].boardName;
      const boardId = boards.length === 0 ? null : boards[0].boardId;
      const selectedBoard = {
        boardName,
        boardId,
      };
      let posts = [];
      if (boardId !== null) {
        posts = await getPosts(boardId).then((res) => res.data);
      }
      setCommunity({
        ...community,
        boards,
        selectedBoard,
        posts,
      });
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    fetchCommunity();
  }, []);

  const category = ['공지', '유머', '연애'];
  const postings = [
    { title: '새로운 곳', user: 'ak123', time: '2021-01' },
    { title: '새로운 곳', user: 'ak123', time: '2021-01' },
  ];

  const pageInfo = { number: '1', maxNumber: '2' };
  return (
    <>
      <CategoryHeader category={category}></CategoryHeader>
      <PostWrapper postings={postings}></PostWrapper>
      <WriteButton></WriteButton>
      <PageButton pageInfo={pageInfo}></PageButton>
    </>
  );
}
