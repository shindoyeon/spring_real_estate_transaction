import {
  addbookmark,
  bookmarkList,
  delBookmark,
  myBookmarkList,
} from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    bookmarkList: [],
    isBookmark: false,
    myBookmarkList: [],
  },
  getters: {},
  mutations: {
    SET_BOOKMARK_LIST(state, payload) {
      state.bookmarkList = payload;
    },
    SET_ISBOOKMARK(state, payload) {
      state.isBookmark = payload;
    },
    SET_MY_BOOKMARK_LIST(state, payload) {
      state.myBookmarkList = payload;
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
    async deleteBookmark({ commit }, params) {
      await delBookmark(
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
    getMyBookmarkList: ({ commit }, params) => {
      myBookmarkList(
        params,
        ({ data }) => {
          commit("SET_MY_BOOKMARK_LIST", data);
          console.log("내북마크리스트 수정완료");
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default bookmarkStore;
