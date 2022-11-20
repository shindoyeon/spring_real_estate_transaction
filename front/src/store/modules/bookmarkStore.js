import { addbookmark, bookmarkList, delBookmark } from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    bookmarkList: [],
    isBookmark: false,
  },
  getters: {},
  mutations: {
    SET_BOOKMARK_LIST(state, payload) {
      state.bookmarkList = payload;
    },
    SET_ISBOOKMARK(state, payload) {
      state.isBookmark = payload;
    },
  },
  actions: {
    insertBookmark: ({ commit }, params) => {
      // console.log("insertBoomark aptCode:" + aptCode);
      // console.log("insertBoomark userId:" + userId);
      addbookmark(
        params,
        ({ data }) => {
          commit("SET_BOOKMARK_LIST", data);
          commit("SET_ISBOOKMARK", true);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteBookmark: ({ commit }, params) => {
      delBookmark(
        params,
        ({ data }) => {
          commit("SET_BOOKMARK_LIST", data);
          commit("SET_ISBOOKMARK", false);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getBookmarkList: ({ commit }, params) => {
      bookmarkList(
        params,
        ({ data }) => {
          commit("SET_BOOKMARK_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default bookmarkStore;
