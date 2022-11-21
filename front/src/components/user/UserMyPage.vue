<template>
  <b-container class="container my-5" v-if="userInfo != null">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>마이 페이지</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

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
          <router-link :to="{ name: 'join' }" class="link"
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
