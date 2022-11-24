import { apiInstance } from "./index.js";

const api = apiInstance();

function listReview(param, success, fail) {
  api.get(`/review/${param}`).then(success).catch(fail);
}
function myListReview(param, success, fail) {
  api.get(`/review/my/${param}`).then(success).catch(fail);
}

async function review(params, success, fail) {
  console.log(params);
  await api.put(`/review/`, params).then(success).catch(fail);
}

async function oneReview(param, success, fail) {
  await api.get(`/review/one`, param).then(success).catch(fail);
}

async function delReview(params, success, fail) {
  console.log(params);
  await api
    .delete(`/review/${params.reviewNo}/${params.userId}`)
    .then(success)
    .catch(fail);
}
export { listReview, review, myListReview, oneReview, delReview };
