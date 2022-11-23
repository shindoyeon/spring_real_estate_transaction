import { kakaoApiInstance } from "./kakao.js";

const api = kakaoApiInstance();

function pharms(params, success, fail) {
  //약국
  api
    .get(
      `/v2/local/search/category.json?category_group_code=PM9&x=${params.x}&y=${params.y}&radius=3000`
    )
    .then(success)
    .catch(fail);
}
export { pharms };
