import TeamMake from "./TeamMake";
import CheckLogin from "../CheckLogin";

export default function TeamMakeContainer({ user }) {
  return (
    <CheckLogin user={user}>
      <TeamMake></TeamMake>
    </CheckLogin>
  );
}
