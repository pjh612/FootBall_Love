import CheckLogin from '../CheckLogin';
import ProfileAvatar from './ProfileAvatar';
import ProfileInfo from './ProfileInfo';
import styled from 'styled-components';

const FlexBox = styled.div`
    display: flex;
    justify-content: center;
`;

const AvatarWrapper = styled.div`
    width: 300px;
    height:100%;
`;

const InfoWrapper = styled.div`
    width: 500px;
    height:100%;
`;


const Profile = ({user}) => {
    return(
        <>
            <CheckLogin user={user}>
                <FlexBox> 
                    <AvatarWrapper>          
                        <ProfileAvatar userImg={user.img}></ProfileAvatar>
                    </AvatarWrapper>
                    <InfoWrapper>
                        <ProfileInfo user={user}></ProfileInfo>
                    </InfoWrapper>
                </FlexBox>
            </CheckLogin>
        </>
    )
}

export default Profile;