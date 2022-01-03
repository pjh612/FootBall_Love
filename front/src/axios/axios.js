import axios from "axios";
import { convertToJsonData } from "../utils/util";

export function sendJoinData(data) {
  const jsonData = convertToJsonData(data);
  return axios({
    method: "post",
    headers: {
      "content-type": "application/json",
    },
    url: `http://127.0.0.1:8080/member`, // 3000 을 8080으로바까야함.
    data: jsonData,
  });
}

export function sendLoginData(data) {
  const jsonData = convertToJsonData(data);
  return axios({
    method: "post",
    headers: {
      "content-type": "application/json",
    },
    url: `http://127.0.0.1:8080/member/login_jwt/${data.id}`,
    data: jsonData,
  });
}

export function getUserInfo(key) {
  return axios({
    method: "get",
    url: `http://127.0.0.1:8080/member/${key}`,
  });
}
