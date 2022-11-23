<template>
  <b-tr>
    <b-td v-text="index + 1"></b-td>
    <b-td v-text="user.userId"></b-td>
    <b-td v-text="user.userEmail"></b-td>
    <b-td>
      <select
        v-if="user.delflag == '0'"
        class="dropdown"
        name="leave"
        id="leave"
      >
        <option value="0" selected>탈퇴안함</option>
        <option value="1">탈퇴</option>
      </select>
      <select v-else class="dropdown" name="leave" id="leave">
        >
        <option value="0" selected>탈퇴안함</option>
        <option value="1" selected>탈퇴</option>
        <!-- <option value="user.delflag" class="dropdown-item">
          {{ user.delflag | showdel }}
        </option>
        <option value="0">탈퇴안함</option>
        <option value="1">탈퇴</option> -->
      </select>
    </b-td>
    <b-td
      ><button
        type="button"
        id="btn-mv-modify"
        class="btn btn-outline-success mb-1 ms-1"
        @click="modify(user.userId)"
      >
        수정
      </button></b-td
    >
  </b-tr>
</template>
<script>
import { apiInstance } from "@/api/index.js";
const api = apiInstance();

export default {
  name: "UserItem",
  props: {
    user: {},
    index: Number,
  },
  filters: {
    showdel(value) {
      return value == 0 ? "탈퇴안함" : "탈퇴";
    },
  },
  methods: {
    modify() {
      var select = document.getElementById("leave");
      console.log(select.options[select.selectedIndex].value);
      let user = {
        userId: this.user.userId,
        delflag: select.options[select.selectedIndex].value,
      };
      // console.log(user);
      api
        .put(`/user/updatedel`, user)
        .then(({ data }) => {
          alert("회원 정보 수정을 완료하였습니다.");
          console.log(data);
        })
        .catch((error) => {
          alert("회원 정보 수정을 실패하였습니다.");
          console.log(error);
        });
    },
  },
};
</script>
<style scoped></style>
