import * as React from "react";
import Avatar from "@mui/material/Avatar";
import { useState } from "react";
import UserMenuList from "./UserMenuList";
import { useSelector } from "react-redux";

const UserAvatar = () => {
  function stringToColor(string) {
    if (!string) {
      return "#000000";
    }
    let hash = 0;
    let i;

    /* eslint-disable no-bitwise */
    for (i = 0; i < string.length; i += 1) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = "#";

    for (i = 0; i < 3; i += 1) {
      const value = (hash >> (i * 8)) & 0xff;
      color += `00${value.toString(16)}`.substr(-2);
    }
    /* eslint-enable no-bitwise */

    return color;
  }

  const [openMenu, setOpenMenu] = useState(false);

  const OpenMenuList = () => {
    setOpenMenu(!openMenu);
  };

  const user = useSelector((state) => state.userReducer.user);

  return (
    <>
      <Avatar
        onClick={() => OpenMenuList()}
        src={
          user.profileUri
            ? `https://storage.googleapis.com/fbl_profile_img/${user.profileUri}`
            : "#"
        }
        sx={{ cursor: "pointer", bgcolor: stringToColor(user.name) }}
      >
        {user.profileUri
          ? null
          : user.name.length <= 2
          ? user.name
          : user.name.slice(1, 3)}
      </Avatar>
      {openMenu ? (
        <UserMenuList setOpenMenu={setOpenMenu}></UserMenuList>
      ) : null}
    </>
  );
};

export default UserAvatar;
