import axios from "axios";
import { convertToJsonData } from "../utils/util";

export function sendJoinData(data) {
  const jsonData = convertToJsonData(data);
  return axios({
    method: "post",
    headers: {
      "content-type": "application/json; charset=UTF-8",
    },
    url: `https://flove.fbl.p-e.kr/api/member`, // 3000 을 8080으로바까야함.
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
    url: `https://flove.fbl.p-e.kr/api/member/login_jwt/${data.id}`,
    data: jsonData,
  });
}

export function getUserInfo(key) {
  return axios({
    method: "get",
    url: `https://flove.fbl.p-e.kr/api/member/${key}`,
  });
}

export function postUserImg(imgFile) {
  return axios({
    method: "post",
    url: "https://flove.fbl.p-e.kr/api/profile_img",
    data: imgFile,
  });
}
