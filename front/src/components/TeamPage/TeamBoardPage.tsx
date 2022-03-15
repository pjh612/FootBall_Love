import { useParams, useNavigate } from 'react-router-dom';
import { getPosts as getItems } from '../../axios/axios';
import { useState, useEffect } from 'react';
import Posting from '../Board/Posting';
type Post = {
  authorId: string;
  authorNumber: number;
  boardId: number;
  comments: string[];
  content: string;
  createBy: null | string;
  createdDate: string;
  id: number;
  lastModifiedBy: null | string;
  lastModifiedDate: string;
  likeCount: number;
  postImages: string[];
  title: string;
};

export default function TeamBoardPage() {
  const navigate = useNavigate();
  const boardId = useParams().boardNumber;
  const [posts, setPosts] = useState<Post[]>([]);

  useEffect(() => {
    getPosts();
  }, []);

  async function getPosts() {
    try {
      const posts = await getItems(boardId).then((res) => res.data);
      console.log(posts.content);
      setPosts(posts.content);
    } catch (err) {
      console.log('팀 포스트 불러오기 실패');
      console.log(err.response);
    }
  }

  return (
    <>
      {posts.map((post) => {
        return <Posting post={post} key={post.title}></Posting>;
      })}
      <button onClick={() => navigate('./write')}>글쓰기</button>
    </>
  );
}
