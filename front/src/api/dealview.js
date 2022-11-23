import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(sido, success, fail) {
  api.get(`/map/gugun/${sido}`).then(success).catch(fail);
}

function dongList(gugun, success, fail) {
  api.get(`/map/dong/${gugun}`).then(success).catch(fail);
}

function houseListByDong(dong, success, fail) {
  api.get(`/house/list/${dong}`).then(success).catch(fail);
}

async function HouseListByKeyword(params, success, fail) {
  await api.get(`/house/list/keyword/${params}`).then(success).catch(fail);
}

function HouseDealList(params, success, fail) {
  api.get(`/house/dealList/${params}`).then(success).catch(fail);
}

export {
  sidoList,
  gugunList,
  dongList,
  houseListByDong,
  HouseListByKeyword,
  HouseDealList,
};
