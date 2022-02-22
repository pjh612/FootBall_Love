import TeamMake from './TeamMake';
import CheckLogin from '../CheckLogin';

export default function TeamMakeContainer() {
  return (
    <CheckLogin>
      <TeamMake></TeamMake>
    </CheckLogin>
  );
}
