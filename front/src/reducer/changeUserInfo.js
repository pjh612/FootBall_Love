function changeUserInfo(state, action) {
  if (state === undefined) {
    return { user: null };
  }

  const updateUserInfo = { ...state };

  switch (action.type) {
    case "CHANGE":
      updateUserInfo;
  }
}
