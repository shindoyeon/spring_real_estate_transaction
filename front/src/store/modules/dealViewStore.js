import {
  sidoList,
  gugunList,
  dongList,
  houseListByDong,
  HouseListByKeyword,
} from "@/api/dealview.js";

const dealViewStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "시" }],
    guguns: [{ value: null, text: "구" }],
    dongs: [{ value: null, text: "동" }],
    houseList: [],
    house: null,
    curIndex: -1,
    listVisible: false,
    // 메인 페이지에서 받을 키워드
    fromMainKeyword: "",
  },
  getters: {},
  mutations: {
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "동" }];
    },
    CLEAR_HOUSE_LIST(state) {
      state.houseList = [];
      state.house = null;
    },
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_HOUSE_LIST(state, payload) {
      state.houseList = payload;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    SET_CURINDEX(state, payload) {
      state.curIndex = payload;
      console.log(state.curIndex);
    },
    SET_LISTVISIBLE(state, payload) {
      state.listVisible = payload;
      console.log(state.listVisible);
    },
  },
  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      gugunList(
        sidoCode,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      dongList(
        gugunCode,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseListByDong: ({ commit }, dongCode) => {
      houseListByDong(
        dongCode,
        ({ data }) => {
          console.log(data);
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
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
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default dealViewStore;
