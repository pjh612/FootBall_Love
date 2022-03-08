import axios from 'axios';
import { convertToJsonData } from '../utils/util';

export function sendJoinData(data) {
  const jsonData = convertToJsonData(data);
  return axios({
    method: 'post',
    headers: {
      'content-type': 'application/json; charset=UTF-8',
    },
    url: `https://flove.fbl.p-e.kr/api/member`,
    data: jsonData,
  });
}

export function logoutRequest() {
  return axios({
    method: 'post',
    url: `https://flove.fbl.p-e.kr/api/member/logout_jwt`,
  });
}

export function sendLoginData(data) {
  const jsonData = convertToJsonData(data);
  return axios({
    method: 'post',
    headers: {
      'content-type': 'application/json',
    },
    url: `https://flove.fbl.p-e.kr/api/member/login_jwt/${data.id}`,
    data: jsonData,
  });
}

export function getUserInfo() {
  return axios({
    method: 'get',
    url: 'https://flove.fbl.p-e.kr/api/member/loginInfo',
  });
}

export function postUserImg(imgFile) {
  return axios({
    method: 'post',
    url: 'https://flove.fbl.p-e.kr/api/profile_img',
    data: imgFile,
  });
}

export function postUserPost(fd) {
  return axios({
    method: 'post',
    url: 'https://flove.fbl.p-e.kr/api/board/post',
    data: fd,
  });
}

export function postTeamInfo(data) {
  return axios({
    method: 'post',
    url: 'https://flove.fbl.p-e.kr/api/team',
    data: data,
  });
}

export function getTeamInfo() {
  return axios({
    method: 'get',
    url: 'https://flove.fbl.p-e.kr/api/team/list',
  });
}

export function getDetailTeamInfo(teamId) {
  return axios({
    method: 'get',
    url: `https://flove.fbl.p-e.kr/api/team/${teamId}`,
  });
}

export function makeBoardRequest(teamId, data) {
  return axios({
    method: 'post',
    url: `https://flove.fbl.p-e.kr/api/team/${teamId}/board`,
    data: data,
  });
}

export function getTeamBoardPosts(boardId) {
  return axios({
    method: 'get',
    params: {
      page: 0,
      size: 10,
    },
    url: `https://flove.fbl.p-e.kr/api/board/${boardId}/post`,
  });
}

export function makeBoardAdmin(data) {
  return axios({
    method: 'post',
    url: 'https://flove.fbl.p-e.kr/api/board',
    data: data,
  });
}

export function getBoardList() {
  return axios({
    method: 'get',
    url: 'https://flove.fbl.p-e.kr/api/board',
  });
}

export function deleteBoard(boardId) {
  return axios({
    method: 'delete',
    url: `https://flove.fbl.p-e.kr/api/board/${boardId}`,
  });
}
