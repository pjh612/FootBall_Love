function updateUserAction(updateThings) {
  return {
    type: "UPDATE_USERINFO",
    update: updateThings,
  };
}

function updateUserProfileUri(newUri) {
  return {
    type: "UPDATE_PROFILE_PHOTO_URI",
    uri: newUri,
  };
}

function updateTeamAction(newTeaminfo) {
  return {
    type: "UPDATE_TEAMINFO",
    teaminfo: newTeaminfo,
  };
}

function logoutAction() {
  return {
    type: "LOGOUT",
  };
}

export {
  updateUserAction,
  updateUserProfileUri,
  updateTeamAction,
  logoutAction,
};
