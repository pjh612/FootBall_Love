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

export { updateUserAction, updateUserProfileUri };
