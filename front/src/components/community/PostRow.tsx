export default function PostRow({ post }) {
  return (
    <li>
      <p>{post.title}</p>
      <p>
        {post.user}
        {'   '}
        {post.time}
      </p>
    </li>
  );
}
