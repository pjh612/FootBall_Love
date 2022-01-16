import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import {useState} from 'react';
import UserMenuList from './UserMenuList';

const UserAvatar = () => {
    const [openMenu, setOpenMenu] = useState(false);

    const OpenMenuList = () => {
        setOpenMenu(!openMenu);
    }

    return(
        <>
        <Avatar onClick={() => OpenMenuList()}src="img/훌쩍1.png" sx={{ width:32, height: 32, cursor:'pointer'}}></Avatar>
        {openMenu ? <UserMenuList setOpenMenu={setOpenMenu}></UserMenuList> : null}
        </>
    )
}

export default UserAvatar