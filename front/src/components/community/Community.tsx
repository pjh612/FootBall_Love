import WriteButton from './WriteButton';
import CategoryHeader from './CategoryHeader';
import PostWrapper from './PostWrapper';
import PageButton from './PageButton';
import { useState } from 'react';

export default function Community() {
  const [community, setCommunity] = useState({
    category: ['공지', '유머', '연애'],
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
