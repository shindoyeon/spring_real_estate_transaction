import { kakaoApiInstance } from "./kakao.js";

const api = kakaoApiInstance();

//약국
async function pharms(params, success, fail) {
  await api
    .get(
      `/v2/local/search/category.json?category_group_code=PM9&x=${params.x}&y=${params.y}&radius=3000`
    )
    .then(success)
    .catch(fail);
}

//지하철
async function subways(params, success, fail) {
  await api
    .get(
      `/v2/local/search/category.json?category_group_code=SW8&x=${params.x}&y=${params.y}&radius=3000`
    )
    .then(success)
    .catch(fail);
}

//은행
async function banks(params, success, fail) {
  await api
    .get(
      `/v2/local/search/category.json?category_group_code=BK9&x=${params.x}&y=${params.y}&radius=3000`
    )
    .then(success)
    .catch(fail);
}

export { pharms, subways, banks };
