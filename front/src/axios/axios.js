import axios from "axios";

export function sendJoinData(data) {
  return axios({
    method: "post",
    url: `http://127.0.0.1:8080/member`, // 3000 을 8080으로바까야함.
    data: data,
  });
}
