import { apiInstance } from "./index.js";

const api = apiInstance();

function addbookmark(params, success, fail) {
  console.log("insertBoomark aptCode:" + params.aptCode);
  console.log("insertBoomark userId:" + params.userId);
  api
    .put(`/bookmark/${params.aptCode}/${params.userId}`)
    .then(success)
    .catch(fail);
}

function delBookmark(params, success, fail) {
  api
    .delete(`/bookmark/${params.aptCode}/${params.userId}`)
    .then(success)
    .catch(fail);
}

function bookmarkList(params, success, fail) {
  api.get(`/bookmark/${params}`).then(success).catch(fail);
}

export { addbookmark, bookmarkList, delBookmark };
