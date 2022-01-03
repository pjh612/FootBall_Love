export function setLocalStorage(key) {
  window.setLocalStorage("key", JSON.stringify(key));
}

export function getLocalStorage() {
  const noItem = null;
  return JSON.parse(window.localStorage.getItem("key")) || noItem;
}
