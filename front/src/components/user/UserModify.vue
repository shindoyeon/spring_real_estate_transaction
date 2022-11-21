<template>
  <b-container class="mt-3">
    <div class="container">
      <main class="form-signin w-100 m-auto">
        <form id="form-join" method="POST" action="">
          <input type="hidden" name="act" value="join" />
          <h1 class="h3 mb-3 fw-normal" style="text-align: center">
            회원정보 수정
          </h1>
          <div class="form-floating mb-1">
            <input
              v-model="userId"
              type="text"
              class="form-control"
              id="userId"
              name="userId"
              placeholder="id"
              readonly
            />
            <label for="userId">id</label>
          </div>
          <div id="idcheck-result"></div>
          <div class="form-floating mb-1">
            <input
              v-model="userName"
              type="text"
              class="form-control"
              id="userName"
              name="userName"
              placeholder="name"
            />
            <label for="userName">name</label>
          </div>
          <div class="form-floating mb-1">
            <input
              v-model="userPassword"
              type="password"
              class="form-control"
              id="userPassword"
              name="userPassword"
              placeholder="password"
            />
            <label for="userPassword">password</label>
          </div>
          <div class="form-floating mb-1">
            <input
              v-model="reuserPassword"
              type="password"
              class="form-control"
              @keyup="pwdcheck"
              id="reuserPassword"
              name="reuserPassword"
              placeholder="reuserPassword"
            />
            <label for="reuserPassword">비밀번호 재입력</label>
          </div>
          <div id="pwdcheck-result"></div>
          <div class="form-floating mb-1">
            <input
              v-model="userEmail"
              type="email"
              class="form-control"
              @keyup="emailcheck"
              id="userEmail"
              name="userEmail"
              placeholder="email"
            />
            <label for="userEmail">email</label>
          </div>
          <div id="emailcheck-result"></div>
          <div class="form-floating mb-1">
            <input
              v-model="userPhone"
              type="text"
              class="form-control"
              id="userPhone"
              name="userPhone"
              placeholder="phone"
            />
            <label for="userPhone">phone</label>
          </div>
          <button
            class="w-100 btn btn-lg btn-primary"
            @click="update"
            id="btn-join"
            type="button"
          >
            회원정보 수정
          </button>
        </form>
      </main>
    </div>
  </b-container>
</template>

<script>
// import { apiInstance } from "@/api/index.js";
import { mapActions, mapState } from "vuex";
// const api = apiInstance();
export default {
  name: "UserModify",
  computed: {
    ...mapState("memberStore", ["userInfo"]),
  },
  data() {
    return {
      userId: "",
      userName: "",
      userPassword: "",
      reuserPassword: "",
      userEmail: "",
      userPhone: "",
      userRole: "",
      userIdChk: false,
      userPwdChk: false,
      userEmailChk: false,
    };
  },
  created() {
    this.userId = this.userInfo.userId;
    this.userName = this.userInfo.userName;
    this.userEmail = this.userInfo.userEmail;
    this.userPhone = this.userInfo.userPhone;
  },

  methods: {
    ...mapActions("memberStore", ["userUpdate"]),
    pwdcheck() {
      let resultDiv = document.querySelector("#pwdcheck-result");
      if (this.userPassword === this.reuserPassword) {
        resultDiv.textContent = "";
        this.userPwdChk = true;
      }
      if (this.userPassword != this.reuserPassword) {
        resultDiv.setAttribute("class", "mb-3 text-danger");
        resultDiv.textContent = "비밀번호를 확인해주세요.";
        this.userPwdChk = false;
      }
    },
    emailcheck() {
      let resultDiv = document.querySelector("#emailcheck-result");

      let regex = new RegExp(
        // eslint-disable-next-line
        "([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])"
      );
      if (regex.test(this.userEmail)) {
        resultDiv.textContent = "";
        this.userEmailChk = true;
      } else {
        resultDiv.setAttribute("class", "mb-3 text-danger");
        resultDiv.textContent = "올바른 이메일 형식이 아닙니다.";
        this.userEmailChk = false;
      }
    },
    update() {
      this.emailcheck();
      this.pwdcheck();
      let user = {
        userId: this.userId,
        userName: this.userName,
        userPassword: this.userPassword,
        userEmail: this.userEmail,
        userPhone: this.userPhone,
        userRole: this.userRole,
      };
      if (!document.querySelector("#userName").value) {
        alert("이름을 입력해주세요.");
        return;
      } else if (!document.querySelector("#userPassword").value) {
        alert("비밀번호를 입력해주세요.");
        return;
      } else if (!document.querySelector("#reuserPassword").value) {
        alert("비밀번호를 재입력해주세요.");
        return;
      } else if (!this.userPwdChk) {
        alert("비밀번호를 확인해주세요.");
        return;
      } else if (!this.userEmailChk) {
        alert("이메일을 확인해주세요.");
        return;
      } else {
        this.userUpdate(user);
        this.$router.push(`/user/mypage`);
      }
    },
  },
};
</script>

<style></style>
