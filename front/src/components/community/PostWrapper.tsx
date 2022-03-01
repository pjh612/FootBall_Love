import PostRow from './PostRow';

export default function PostWrapper({ postings }) {
  const postRows = postings.map((post) => {
    return <PostRow key={post.title} post={post}></PostRow>;
  });
  return <div>{postRows}</div>;
}
