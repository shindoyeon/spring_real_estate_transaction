<template>
  <!-- Basic Bootstrap Table -->
  <div class="card">
    <h5 class="card-header">회원 관리 테이블</h5>
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
            <th>회원아이디</th>
            <th>회원이메일</th>
            <th>탈퇴 여부</th>
            <th>수정</th>
          </tr>
        </thead>
        <tbody class="table-border-bottom-0">
          <user-item
            v-for="(user, index) in users"
            :no="index"
            :key="index"
            :user="user"
            :index="index"
          ></user-item>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
import { apiInstance } from "@/api/index.js";
import UserItem from "@/components/user/UserItem.vue";
const api = apiInstance();

export default {
  name: "UserManage",
  components: {
    UserItem,
  },
  data() {
    return {
      users: [],
    };
  },
  created() {
    api
      .get("/user/list")
      .then(({ data }) => {
        this.users = data;
        // console.log(data);
      })
      .catch(() => {
        console.log("error");
      });
  },
};
</script>
<style scoped></style>
