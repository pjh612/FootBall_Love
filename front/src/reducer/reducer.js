function userReducer(state, action) {
  if (state === undefined) {
    return {
      user: null,
    };
  }

  const newState = { ...state };

  switch (action.type) {
    case "UPDATE_USERINFO":
      return {
        ...state,
        user: { ...action.update },
      };

    case "UPDATE_PROFILE_PHOTO_URI":
      newState.user.profileUri = action.uri;
      return { ...newState };

    default:
      return { ...state };
  }
}

export { userReducer };
