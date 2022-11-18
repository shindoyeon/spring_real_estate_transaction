import { HouseListByKeyword } from "@/api/dealview.js";

const dealViewStore = {
  namespaced: true,
  state: {
    gu: [],
    dong: [],
    houseList: [],

    // 메인 페이지에서 받을 키워드
    fromMainKeyword: "",
  },
  getters: {},
  mutations: {
    SET_HOUSE_LIST(state, payload) {
      state.houseList = payload;
    },
  },
  actions: {
    getHouseListByKeyword({ commit }, keyword) {
      //const params = { keyword: keyword };
      console.log(keyword);
      HouseListByKeyword(
        keyword,
        ({ data }) => {
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default dealViewStore;
