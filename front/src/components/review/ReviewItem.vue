<template>
  <b-tr>
    <b-td v-text="index + 1"></b-td>
    <b-td v-text="review.apartmentName"></b-td>
    <b-td v-text="review.content"></b-td>
    <b-td v-text="review.registerTime"></b-td>
    <b-td
      ><button
        type="button"
        id="btn-mv-modify"
        class="btn btn-outline-success mb-1 ms-1"
        @click="deleteReivew"
      >
        삭제
      </button></b-td
    >
  </b-tr>
</template>
<script>
import { mapActions, mapState } from "vuex";
export default {
  name: "ReviewItem",
  props: {
    review: {},
    index: Number,
  },
  data() {
    return {
      houseIndex: -1,
    };
  },
  computed: {
    ...mapState("memberStore", ["userInfo"]),
    ...mapState("reviewStore", ["reviewList"]),
    ...mapState("dealViewStore", ["houseList", "houseName"]),
  },
  methods: {
    ...mapActions("reviewStore", ["deleteReviewOne"]),
    deleteReivew() {
      let reviewDto = {
        reviewNo: this.review.reviewNo,
        userId: this.review.userId,
      };
      this.deleteReviewOne(reviewDto);
    },
  },
};
</script>
<style scoped></style>
