import { apiInstance } from "./index.js";

const api = apiInstance();

function HouseListByKeyword(params, success, fail) {
  api.get(`/house/list/keyword/${params}`).then(success).catch(fail);
}

export { HouseListByKeyword };
