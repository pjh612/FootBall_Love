import CheckLogin from '../CheckLogin';
import ProfileAvatar from './ProfileAvatar';

const Profile = ({user}) => {
    return(
        <>
            <CheckLogin user={user}>
                <div>               
                     <h1>ThisisProfile</h1>
                    <ProfileAvatar></ProfileAvatar>
                </div>
            </CheckLogin>
        </>
    )
}

export default Profile;