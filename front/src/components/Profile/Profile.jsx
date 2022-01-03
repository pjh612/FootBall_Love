import CheckLogin from '../CheckLogin';

const Profile = ({user}) => {
    return(
        <>
            <CheckLogin user={user}>
                <h1>ThisisProfile</h1>
            </CheckLogin>
        </>
    )
}

export default Profile;