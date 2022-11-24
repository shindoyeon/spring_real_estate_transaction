<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">공지사항</h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row my-2" style="text-align: left padding:px margin:5px">
        <h3 class="text-secondary px-5">{{ articleNo }}.{{ subject }}</h3>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="clearfix m-2" style="text-align: left">
            <img
              class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            />
            <p>
              <span class="fw-bold m-2">{{ userId }}</span> <br />
              <span class="text-secondary fw-light m-2">
                {{ registerTime }}
              </span>
              <span class="text-secondary fw-light m-2">
                조회 : {{ hit }}
              </span>
            </p>
          </div>
        </div>
        <div class="divider mb-3"></div>
        <div class="text-secondary m-2" style="text-align: left">
          {{ content }}
        </div>
        <div class="divider mt-3 mb-3"></div>
        <div class="d-flex justify-content-end">
          <router-link to="/board">
            <button
              type="button"
              id="btn-list"
              class="btn btn-outline-primary mb-3 ms-3"
            >
              글목록
            </button>
          </router-link>
          <button
            type="button"
            id="btn-mv-modify"
            class="btn btn-outline-success mb-3 ms-3"
            @click="moveModify(articleNo)"
            v-if="userInfo.userRole == 1"
          >
            글수정
          </button>
          <button
            type="button"
            id="btn-delete"
            class="btn btn-outline-danger mb-3 ms-1 ms-3"
            @click="deleteArticle(articleNo)"
            v-if="userInfo.userRole == 1"
          >
            글삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { apiInstance } from "@/api/index.js";
import { mapState } from "vuex";
const api = apiInstance();
const memberStore = "memberStore";

export default {
  name: "BoardDetail",
  data() {
    return {
      articleNo: "",
      userId: "",
      subject: "",
      content: "",
      registerTime: "",
      hit: 0,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    let no = this.$route.params.no;

    api
      .get(`/board/detail/${no}`)
      .then(({ data }) => {
        this.articleNo = data.articleNo;
        this.userId = data.userId;
        this.subject = data.subject;
        this.content = data.content;
        this.registerTime = data.registerTime;
        this.hit = data.hit;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    moveModify(no) {
      this.$router.push(`/board/boardmodify/${no}`);
    },
    deleteArticle(no) {
      api.delete(`/board/delete/${no}`).then(({ data }) => {
        let msg = "삭제 처리시 문제가 발생했습니다.";
        if (data === "success") {
          msg = "삭제가 완료되었습니다.";
        }
        alert(msg);
        // 현재 route를 /list로 변경.
        this.$router.push({ name: "list" });
      });
    },
  },
};
</script>
<style scoped></style>
