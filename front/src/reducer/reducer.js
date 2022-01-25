import produce from "immer";

function userReducer(state, action) {
  if (state === undefined) {
    return {
      user: null,
    };
  }

  switch (action.type) {
    case "UPDATE_USERINFO":
      return {
        ...state,
        user: { ...action.update },
      };

    case "UPDATE_PROFILE_PHOTO_URI":
      return produce(state, (draft) => {
        const newUri = action.uri;
        draft.user.profileUri = newUri;
      });

    case "LOGOUT":
      return produce(state, (draft) => {
        draft.user = null;
      });

    default:
      return { ...state };
  }
}

export { userReducer };
