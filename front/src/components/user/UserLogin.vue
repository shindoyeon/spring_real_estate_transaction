<template>
  <b-container class="mt-3">
    <div class="container">
      <div class="form-structor m-auto">
        <div class="signup">
          <h2 class="form-title" id="signup" ref="signup" @click="signupclick">
            <span>or</span>Login
          </h2>
          <b-alert show variant="danger" v-if="isLoginError"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
          >
          <div class="form-holder">
            <input
              type="text"
              class="input"
              id="userId"
              v-model="loginuser.userId"
              required
              @keyup.enter="confirm"
              placeholder="Id"
            />
            <input
              type="password"
              class="input"
              id="userPassword"
              v-model="loginuser.userPassword"
              required
              @keyup.enter="confirm"
              placeholder="Password"
            />
          </div>
          <button class="submit-btn" @click="confirm">login</button>
          <button class="submit-btn" @click="findpassword">
            find password
          </button>
        </div>
        <div class="login slide-up">
          <div class="center">
            <h2 class="form-title" id="login" ref="login" @click="loginclick">
              <span>or</span>Sign up
            </h2>
            <div class="form-holder">
              <input
                v-model="signupuser.userId"
                type="text"
                class="input"
                @keyup="idcheck"
                id="signupuser_userId"
                name="signupuser_userId"
                placeholder="Id"
              />
              <div id="idcheck-result"></div>
              <input
                v-model="signupuser.userName"
                type="text"
                class="input"
                id="signupuser_userName"
                name="signupuser_userName"
                placeholder="Name"
              />
              <input
                v-model="signupuser.userPassword"
                type="password"
                class="input"
                id="signupuser_userPassword"
                name="signupuser_userPassword"
                placeholder="Password"
              />
              <input
                v-model="signupuser.reuserPassword"
                type="password"
                class="input"
                @keyup="pwdcheck"
                id="signupuser_reuserPassword"
                name="signupuser_reuserPassword"
                placeholder="비밀번호 재입력"
              />
              <div id="pwdcheck-result"></div>
              <input
                v-model="signupuser.userEmail"
                type="email"
                class="input"
                @keyup="emailcheck"
                id="signupuser_userEmail"
                name="signupuser_userEmail"
                placeholder="Email"
              />
              <div id="emailcheck-result"></div>
              <input
                v-model="signupuser.userPhone"
                type="text"
                class="input"
                id="signupuser_userPhone"
                name="signupuser_userPhone"
                placeholder="Phone"
              />
            </div>
            <button class="submit-btn" @click="join" id="btn-join">
              signup
            </button>
          </div>
        </div>
      </div>
    </div>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { apiInstance } from "@/api/index.js";

const api = apiInstance();
const memberStore = "memberStore";

export default {
  name: "UserLogin",
  data() {
    return {
      loginuser: {
        userId: null,
        userPassword: null,
      },
      signupuser: {
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
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    ...mapActions("bookmarkStore", ["getBookmarkList"]),
    ...mapActions("reviewStore", ["getReviewList", "getMyReviewList"]),
    async confirm() {
      await this.userConfirm(this.loginuser);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        await this.getBookmarkList(this.userInfo.userId);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push({ name: "home" });
      }
    },
    loginclick(event) {
      const signupBtn = this.$refs.signup; //회원가입
      let parent = event.target.parentNode.parentNode;
      Array.from(event.target.parentNode.parentNode.classList).find(
        (element) => {
          if (element !== "slide-up") {
            parent.classList.add("slide-up");
          } else {
            signupBtn.parentNode.classList.add("slide-up");
            parent.classList.remove("slide-up");
          }
        }
      );
    },
    signupclick(event) {
      const loginBtn = this.$refs.login; //회원가입
      let parent = event.target.parentNode;
      Array.from(event.target.parentNode.classList).find((element) => {
        if (element !== "slide-up") {
          parent.classList.add("slide-up");
        } else {
          loginBtn.parentNode.parentNode.classList.add("slide-up");
          parent.classList.remove("slide-up");
        }
      });
    },
    movePage() {
      this.$router.push({ name: "join" });
    },

    findpassword() {
      this.$router.push({ name: "findpwd" });
    },
    idcheck() {
      let resultDiv = document.querySelector("#idcheck-result");
      if (
        this.signupuser.userId.length < 6 ||
        this.signupuser.userId.length > 16
      ) {
        resultDiv.setAttribute("class", "mb-3 text-dark");
        resultDiv.textContent = "아이디는 6자 이상 16자 이하 입니다.";
        this.signupuser.userIdChk = false;
      } else {
        api.get(`/user/idcheck/${this.signupuser.userId}`).then(({ data }) => {
          if (data == 0) {
            resultDiv.setAttribute("class", "mb-3 text-primary");
            resultDiv.textContent =
              this.signupuser.userId + "는 사용할 수 있습니다.";
            this.signupuser.userIdChk = true;
          } else {
            resultDiv.setAttribute("class", "mb-3 text-danger");
            resultDiv.textContent =
              this.signupuser.userId + "는 사용할 수 없습니다.";
            this.signupuser.userIdChk = false;
          }
        });
      }
    },
    pwdcheck() {
      let resultDiv = document.querySelector("#pwdcheck-result");
      if (this.signupuser.userPassword === this.signupuser.reuserPassword) {
        resultDiv.textContent = "";
        this.signupuser.userPwdChk = true;
      }
      if (this.signupuser.userPassword != this.signupuser.reuserPassword) {
        resultDiv.setAttribute("class", "mb-3 text-danger");
        resultDiv.textContent = "비밀번호를 확인해주세요.";
        this.signupuser.userPwdChk = false;
      }
    },
    emailcheck() {
      let resultDiv = document.querySelector("#emailcheck-result");

      let regex = new RegExp(
        // eslint-disable-next-line
        "([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])"
      );
      if (regex.test(this.signupuser.userEmail)) {
        resultDiv.textContent = "";
        this.signupuser.userEmailChk = true;
      } else {
        resultDiv.setAttribute("class", "mb-3 text-danger");
        resultDiv.textContent = "올바른 이메일 형식이 아닙니다.";
        this.signupuser.userEmailChk = false;
      }
    },
    join() {
      let user = {
        userId: this.signupuser.userId,
        userName: this.signupuser.userName,
        userPassword: this.signupuser.userPassword,
        userEmail: this.signupuser.userEmail,
        userPhone: this.signupuser.userPhone,
        userRole: this.signupuser.userRole,
      };
      if (user.userName.value) {
        alert("이름을 입력해주세요.");
        return;
      } else if (user.userId.value) {
        alert("아이디를 입력해주세요.");
        return;
      } else if (!document.querySelector("#signupuser_userPassword").value) {
        alert("비밀번호를 입력해주세요.");
        return;
      } else if (!document.querySelector("#signupuser_reuserPassword").value) {
        alert("비밀번호를 재입력해주세요.");
        return;
      } else if (!this.signupuser.userIdChk) {
        alert("아이디를 확인해주세요.");
        return;
      } else if (!this.signupuser.userPwdChk) {
        alert("비밀번호를 확인해주세요.");
        return;
      } else if (!this.signupuser.userEmailChk) {
        alert("이메일을 확인해주세요.");
        return;
      } else {
        console.log(user);
        api.post(`/user/join`, user).then(({ data }) => {
          if (data.message === "success") {
            alert("회원가입에 성공하였습니다.");
            this.$router.push(`/`);
          } else {
            alert("회원가입에 실패하였습니다.");
          }
        });
      }
    },
  },
};
</script>

<style scoped lang="scss">
@import url("https://fonts.googleapis.com/css?family=Fira+Sans");

.form-structor {
  background-color: #222;
  border-radius: 15px;
  height: 700px;
  width: 500px;
  position: relative;
  overflow: hidden;

  &::after {
    content: "";
    opacity: 0.8;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-repeat: no-repeat;
    background-position: left bottom;
    background-size: 500px;
    background-image: url("../../assets/images/loginimage.jpg");
  }

  .signup {
    position: absolute;
    top: 35%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    width: 65%;
    z-index: 5;
    -webkit-transition: all 0.3s ease;

    &.slide-up {
      top: 5%;
      -webkit-transform: translate(-50%, 0%);
      -webkit-transition: all 0.3s ease;
    }

    &.slide-up .form-holder,
    &.slide-up .submit-btn {
      opacity: 0;
      visibility: hidden;
    }

    &.slide-up .form-title {
      font-size: 1em;
      cursor: pointer;
    }

    &.slide-up .form-title span {
      margin-right: 5px;
      opacity: 1;
      visibility: visible;
      -webkit-transition: all 0.3s ease;
    }

    .form-title {
      color: #fff;
      font-size: 1.7em;
      text-align: center;

      span {
        color: rgba(0, 0, 0, 0.4);
        opacity: 0;
        visibility: hidden;
        -webkit-transition: all 0.3s ease;
      }
    }

    .form-holder {
      border-radius: 15px;
      background-color: #fff;
      overflow: hidden;
      margin-top: 30px;
      opacity: 1;
      visibility: visible;
      -webkit-transition: all 0.3s ease;

      .input {
        border: 0;
        outline: none;
        box-shadow: none;
        display: block;
        height: 50px;
        line-height: 50px;
        padding: 8px 15px;
        border-bottom: 1px solid #eee;
        width: 100%;
        font-size: 15px;

        &:last-child {
          border-bottom: 0;
        }
        &::-webkit-input-placeholder {
          color: rgba(0, 0, 0, 0.4);
        }
      }
    }

    .submit-btn {
      background-color: rgba(0, 0, 0, 0.4);
      color: rgba(256, 256, 256, 0.7);
      border: 0;
      border-radius: 15px;
      display: block;
      margin: 15px auto;
      padding: 15px 45px;
      width: 100%;
      font-size: 13px;
      font-weight: bold;
      cursor: pointer;
      opacity: 1;
      visibility: visible;
      -webkit-transition: all 0.3s ease;

      &:hover {
        transition: all 0.3s ease;
        background-color: rgba(0, 0, 0, 0.8);
      }
    }
  }

  .login {
    position: absolute;
    top: 15%;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: #fff;
    z-index: 5;
    -webkit-transition: all 0.3s ease;

    &::before {
      content: "";
      position: absolute;
      left: 50%;
      top: -20px;
      -webkit-transform: translate(-50%, 0);
      background-color: #fff;
      width: 200%;
      height: 250px;
      border-radius: 50%;
      z-index: 4;
      -webkit-transition: all 0.3s ease;
    }

    .center {
      position: absolute;
      top: calc(50% - 10%);
      left: 50%;
      -webkit-transform: translate(-50%, -50%);
      width: 65%;
      z-index: 5;
      -webkit-transition: all 0.3s ease;

      .form-title {
        color: #000;
        font-size: 1.7em;
        text-align: center;

        span {
          color: rgba(0, 0, 0, 0.4);
          opacity: 0;
          visibility: hidden;
          -webkit-transition: all 0.3s ease;
        }
      }

      .form-holder {
        border-radius: 15px;
        background-color: #fff;
        border: 1px solid #eee;
        overflow: hidden;
        margin-top: 50px;
        opacity: 1;
        visibility: visible;
        -webkit-transition: all 0.3s ease;

        .input {
          border: 0;
          outline: none;
          box-shadow: none;
          display: block;
          height: 50px;
          line-height: 50px;
          padding: 8px 15px;
          border-bottom: 1px solid #eee;
          width: 100%;
          font-size: 15px;

          &:last-child {
            border-bottom: 0;
          }
          &::-webkit-input-placeholder {
            color: rgba(0, 0, 0, 0.4);
          }
        }
      }

      .submit-btn {
        background-color: #6b92a4;
        color: rgba(256, 256, 256, 0.7);
        border: 0;
        border-radius: 15px;
        display: block;
        margin: 15px auto;
        padding: 15px 45px;
        width: 100%;
        font-size: 13px;
        font-weight: bold;
        cursor: pointer;
        opacity: 1;
        visibility: visible;
        -webkit-transition: all 0.3s ease;

        &:hover {
          transition: all 0.3s ease;
          background-color: rgba(0, 0, 0, 0.8);
        }
      }
    }

    &.slide-up {
      top: 90%;
      -webkit-transition: all 0.3s ease;
    }

    &.slide-up .center {
      top: 10%;
      -webkit-transform: translate(-50%, 0%);
      -webkit-transition: all 0.3s ease;
    }

    &.slide-up .form-holder,
    &.slide-up .submit-btn {
      opacity: 0;
      visibility: hidden;
      -webkit-transition: all 0.3s ease;
    }

    &.slide-up .form-title {
      font-size: 1em;
      margin: 0;
      padding: 0;
      cursor: pointer;
      -webkit-transition: all 0.3s ease;
    }

    &.slide-up .form-title span {
      margin-right: 5px;
      opacity: 1;
      visibility: visible;
      -webkit-transition: all 0.3s ease;
    }
  }
}
</style>
