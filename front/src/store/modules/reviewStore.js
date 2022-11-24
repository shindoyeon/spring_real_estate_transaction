import { listReview, review } from "@/api/review.js";

const reviewStore = {
  namespaced: true,
  state: {
    reviewList: [],
  },
  getters: {},
  mutations: {
    SET_REVIEW_LIST(state, payload) {
      state.reviewList = payload;
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
    insertReview: ({ commit }, params) => {
      //   console.log(params);
      review(
        params,
        ({ data }) => {
          commit("SET_REVIEW_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default reviewStore;
