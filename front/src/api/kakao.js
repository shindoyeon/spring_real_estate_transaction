import axios from "axios";

// local vue api axios instance
function kakaoApiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_KAKAO_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      Authorization: "KakaoAK " + process.env.VUE_APP_API_KAKAO_KEY,
    },
  });
  return instance;
}

export { kakaoApiInstance };
