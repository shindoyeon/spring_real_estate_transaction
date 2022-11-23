import { pharms } from "@/api/kakaoMap.js";

const kakaoStore = {
  namespaced: true,
  state: {
    pharmsList: [],
  },
  getters: {},
  mutations: {
    SET_PHARMS_LIST(state, payload) {
      state.pharmsList = payload;
    },
  },
  actions: {
    getPharms: ({ commit }, params) => {
      pharms(
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
  },
};

export default kakaoStore;
