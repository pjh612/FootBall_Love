import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import { deepOrange, deepPurple } from '@mui/material/colors';
const UserAvatar = ({user}) => {
    return(
        <Avatar sx={{ bgcolor: deepOrange[500], width:32, height: 32, cursor:'pointer'}}>NK</Avatar>
    )
}

export default UserAvatar