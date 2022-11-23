<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원탈퇴</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError"
              >아이디 또는 비밀번호를 확인하세요.</b-alert
            >
            <b-form-group label="비밀번호:" label-for="userPassword">
              <b-form-input
                type="password"
                id="userPassword"
                v-model="user.userPassword"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >회원탈퇴</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserDelete",
  data() {
    return {
      user: {
        userPassword: null,
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo", "userDelete"]),
    ...mapActions("bookmarkStore", ["getBookmarkList"]),
    async confirm() {
      if (this.userInfo.userPassword != this.user.userPassword) {
        alert("비밀번호를 정확히 입력해주십시오.");
        return;
      } else {
        this.userDelete(this.userInfo.userId);
        console.log("회원삭제 완료!");
      }
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style></style>
