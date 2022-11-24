import { apiInstance } from "./index.js";

const api = apiInstance();

function listReview(param, success, fail) {
  api.get(`/review/${param}`).then(success).catch(fail);
}

function review(params, success, fail) {
  console.log(params);
  api.put(`/review/`, params).then(success).catch(fail);
}

export { listReview, review };
