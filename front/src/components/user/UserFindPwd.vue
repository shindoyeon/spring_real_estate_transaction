<template>
  <div>
    <!-- <BasicHeader name="비밀번호 찾기" /> -->
    <div class="container my-5">
      <div class="d-flex flex-column align-items-center">
        <h2>회원가입 시 등록한 아이디와 이메일 입력해주세요.</h2>
        <form class="row g-1 p-0 p-md-4" style="max-width: 32rem">
          <div class="col-12">
            <div class="mb-2">
              <label class="form-label" for="userId">아이디</label>
              <input
                v-model="userId"
                id="userId"
                type="text"
                class="form-control form-control-lg"
                placeholder="아이디를 입력하세요"
              />
            </div>
          </div>
          <div class="col-12">
            <div class="mb-2">
              <div class="form-label" for="userEmail">이메일</div>
              <input
                v-model="userEmail"
                id="userEmail"
                type="email"
                class="form-control form-control-lg"
                placeholder="이메일을 입력하세요"
              />
            </div>
          </div>
          <div class="col-12 text-center mt-4">
            <a
              @click="confirmBtn"
              class="w-100 btn btn-lg btn-block btn-primary lift text-uppercase"
              >비밀번호 찾기</a
            >
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { apiInstance } from "@/api/index.js";

const api = apiInstance();

export default {
  name: "UserFindPwd",
  data() {
    return {
      userId: "",
      userEmail: "",
    };
  },

  methods: {
    confirmBtn() {
      let user = {
        userId: this.userId,
        userEmail: this.userEmail,
      };
      api
        .put(`/user/password`, user)
        .then(({ data }) => {
          console.log(data);
          alert("등록하신 이메일로 임시 비밀번호를 발급하였습니다.");
        })
        .catch((error) => {
          console.log("FindPW: error : ", error);

          if (error.response.status == "404") {
            alert("임시 비밀번호 발급을 실패하였습니다.");
          } else {
            this.$swal("서버에 문제가 발생하였습니다.", { icon: "error" });
          }
        });
    },
  },
};
</script>

<style></style>
