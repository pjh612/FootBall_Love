import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import { useNavigate } from "react-router-dom";

const UserMenuList = ({ setOpenMenu }) => {
  // MenuName & Link
  const ListMenu = [
    ["나의 프로필", "/profile"],
    ["로그아웃", "logout"],
  ];
  const navigate = useNavigate();
  return (
    <List
      sx={{
        width: "100%",
        maxWidth: "100px",
        bgcolor: "background.paper",
        overflow: "auto",
        maxHeight: 300,
        position: "absolute",
        zIndex: 10,
        top: "56px",
        right: "20%",
        "& ul": { padding: 0 },
        border: 1,
        borderColor: "black",
      }}
    >
      {ListMenu.map((item) => (
        <ListItem
          onClick={() => {
            navigate(item[1]);
            setOpenMenu(false);
          }}
          key={item[0]}
          sx={{ height: 40, cursor: "pointer" }}
        >
          <ListItemText
            primaryTypographyProps={{ fontSize: "13px" }}
            primary={`${item[0]}`}
          />
        </ListItem>
      ))}
    </List>
  );
};

export default UserMenuList;
