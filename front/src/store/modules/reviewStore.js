import {
  listReview,
  review,
  myListReview,
  oneReview,
  delReview,
} from "@/api/review.js";

const reviewStore = {
  namespaced: true,
  state: {
    reviewList: [],
    myReviewList: [],
  },
  getters: {},
  mutations: {
    SET_REVIEW_LIST(state, payload) {
      state.reviewList = payload;
    },
    SET_MY_REVIEW_LIST(state, payload) {
      state.myReviewList = payload;
    },
    ADD_MY_REVIEW_LIST(state, payload) {
      state.myReviewList.push(payload);
    },
  },
  actions: {
    getReviewList: ({ commit }, params) => {
      commit("SET_REVIEW_LIST", []);
      listReview(
        params,
        ({ data }) => {
          commit("SET_REVIEW_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getMyReviewList: ({ commit }, params) => {
      commit("SET_MY_REVIEW_LIST", []);
      myListReview(
        params,
        ({ data }) => {
          commit("SET_MY_REVIEW_LIST", data);
          console.log("my review list :", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async insertReview({ commit }, params) {
      console.log("insert Review param: " + params);
      await review(
        params,
        ({ data }) => {
          commit("SET_REVIEW_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
      await oneReview(
        params,
        ({ data }) => {
          commit("ADD_MY_REVIEW_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteReviewOne({ commit }, param) {
      await delReview(
        param,
        ({ data }) => {
          commit("SET_MY_REVIEW_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default reviewStore;
