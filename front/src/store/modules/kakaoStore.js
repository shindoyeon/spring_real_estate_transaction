import { pharms, subways, banks } from "@/api/kakaoMap.js";

const kakaoStore = {
  namespaced: true,
  state: {
    pharmsList: [],
    subwaysList: [],
    banksList: [],
  },
  getters: {},
  mutations: {
    SET_PHARMS_LIST(state, payload) {
      state.pharmsList = payload;
    },
    SET_SUBWAYS_LIST(state, payload) {
      state.subwaysList = payload;
    },
    SET_BANKS_LIST(state, payload) {
      state.banksList = payload;
    },
    CLEAR_ALL_INFRA(state) {
      state.pharmsList = [];
      state.subwaysList = [];
      state.banksList = [];
    },
  },
  actions: {
    async getPharms({ commit }, params) {
      await pharms(
        params,
        ({ data }) => {
          commit("SET_PHARMS_LIST", data.documents);
          console.log("약국 가져오기 완료!", data.documents);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getSubways({ commit }, params) {
      await subways(
        params,
        ({ data }) => {
          commit("SET_SUBWAYS_LIST", data.documents);
          console.log("지하철 가져오기 완료!", data.documents);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getBanks({ commit }, params) {
      await banks(
        params,
        ({ data }) => {
          commit("SET_BANKS_LIST", data.documents);
          console.log("은행 가져오기 완료!", data.documents);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default kakaoStore;
