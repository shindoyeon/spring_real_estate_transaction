<template>
  <div>
    <b-container class="container my-3" v-if="userInfo != null">
      <b-row>
        <b-col>
          <h5 class="card-header">마이 페이지</h5>
        </b-col>
      </b-row>
      <b-row style="padding: 1%"></b-row>
      <b-row class="form-structor m-3">
        <b-col style="text-align: left">
          <b-jumbotron bg-variant="light">
            <h2>My page</h2>
            <hr class="my-4" style="width: 60%" />
            <b-container class="mt-4">
              <b-row>
                <b-col cols="1" align-self="start">아이디</b-col
                ><b-col align-self="start">{{ userInfo.userId }}</b-col>
              </b-row>
              <b-row>
                <b-col cols="1" align-self="start">이름</b-col
                ><b-col align-self="start">{{ userInfo.userName }}</b-col>
              </b-row>
              <b-row>
                <b-col cols="1" align-self="start">이메일</b-col
                ><b-col align-self="start">{{ userInfo.userEmail }}</b-col>
              </b-row>
            </b-container>
            <hr class="my-4" style="width: 60%" />

            <router-link :to="{ name: 'usermodify' }" class="link"
              ><b-button variant="primary" class="mr-1"
                >정보수정</b-button
              ></router-link
            >
            <router-link :to="{ name: 'userdelete' }" class="link"
              ><b-button variant="danger">회원탈퇴</b-button></router-link
            >
          </b-jumbotron>
        </b-col>
      </b-row>
    </b-container>

    <!-- My 리뷰 테이블 -->
    <div class="card">
      <h5 class="card-header">나의 리뷰 리스트</h5>
      <div class="table-responsive text-nowrap">
        <table class="table">
          <colgroup>
            <col style="width: 5%" />
            <col style="width: 30%" />
            <col style="width: 35%" />
            <col style="width: 15%" />
            <col style="width: 15%" />
          </colgroup>
          <thead>
            <tr>
              <th>번호</th>
              <th>아파트이름</th>
              <th>리뷰내용</th>
              <th>작성날짜</th>
              <th>삭제</th>
            </tr>
          </thead>
          <tbody class="table-border-bottom-0">
            <review-item
              v-for="(review, index) in myReviewList"
              :no="index"
              :key="index"
              :review="review"
              :index="index"
            ></review-item>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import ReviewItem from "@/components/review/ReviewItem.vue";
const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {
    ReviewItem,
  },
  mounted() {
    this.getMyReviewList(this.userInfo.userId);
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState("reviewStore", ["myReviewList"]),
  },
  methods: {
    ...mapActions("reviewStore", ["getMyReviewList"]),
  },
};
</script>

<style></style>
