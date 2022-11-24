import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
// module import

import dealViewStore from "./modules/dealViewStore";
import memberStore from "./modules/memberStore";
import bookmarkStore from "./modules/bookmarkStore";
import kakaoStore from "./modules/kakaoStore";
import reviewStore from "./modules/reviewStore";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    dealViewStore,
    memberStore,
    bookmarkStore,
    kakaoStore,
    reviewStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
