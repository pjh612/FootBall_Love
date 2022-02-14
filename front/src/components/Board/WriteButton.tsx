import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';

export default function WriteButton() {
  const navigate = useNavigate();
  return (
    <Button variant="text" onClick={() => navigate('./write')}>
      글쓰기
    </Button>
  );
}
