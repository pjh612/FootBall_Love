import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import {useState} from 'react';
import UserMenuList from './UserMenuList';

const UserAvatar = ({user}) => {
    const [openMenu, setOpenMenu] = useState(false);

    const OpenMenuList = (e) => {
        setOpenMenu(!openMenu);
    }

    return(
        <>
        <Avatar onClick={() => OpenMenuList()}src="img/훌쩍1.png" sx={{ width:32, height: 32, cursor:'pointer'}}></Avatar>
        {openMenu ? <UserMenuList></UserMenuList> : null}
        </>
    )
}

export default UserAvatar