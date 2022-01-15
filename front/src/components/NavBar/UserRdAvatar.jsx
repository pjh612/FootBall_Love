import Avatar from "@mui/material/Avatar";

export default function UserRdAvatar({ user }) {
  function stringToColor(string) {
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
  function stringAvatar(name) {
    return {
      sx: {
        bgcolor: stringToColor(name),
      },
      children:
        name.length > 2 ? `${name[1]}${name[2]}` : `${name[0]}${name[1]}`,
    };
  }

  return (
    <>
      <Avatar {...stringAvatar(user.name)}></Avatar>
    </>
  );
}
