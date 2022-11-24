<template>
  <b-container class="container my-5" v-if="userInfo != null">
    <b-row>
      <b-col>
        <h5 class="card-header">마이 페이지</h5>
      </b-col>
    </b-row>
    <b-row style="padding: 1%"></b-row>
    <b-row class="form-structor m-auto">
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <h2>My page</h2>
          <hr class="my-4" />
          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userId }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.userName
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start">{{
                userInfo.userEmail
              }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

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
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
};
</script>

<style></style>
