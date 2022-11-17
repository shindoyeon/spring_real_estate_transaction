<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글수정</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-modify" method="POST" action="">
        <input type="hidden" name="act" value="modify" />
        <input type="hidden" name="articleno" v-model="articleNo" />
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input
            type="text"
            v-model="subject"
            class="form-control"
            id="subject"
            name="subject"
            ref="subject"
          />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea
            v-model="content"
            class="form-control"
            id="content"
            name="content"
            ref="content"
            rows="7"
          >
          </textarea>
        </div>
        <div class="col-auto text-center">
          <button
            type="button"
            id="btn-modify"
            class="btn btn-outline-primary mb-3"
            @click="updateArticle()"
          >
            글수정
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-danger mb-3"
          >
            목록으로이동...
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import http from "@/api/http.js";
export default {
  name: "BoardModify",
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
  created() {
    let no = this.$route.params.no;
    http
      .get(`/detail/${no}`)
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
    moveBoard(no) {
      this.$router.push(`/board/detail/${no}`);
    },
    moveList() {
      this.$router.push(`/board`);
    },
    updateArticle() {
      let myData = {
        articleNo: this.articleNo,
        userId: this.userId,
        subject: this.subject,
        content: this.content,
      };
      http
        .put(`/modify/`, myData)
        .then(({ data }) => {
          if (data != null) alert("성공적으로 글을 수정하였습니다.");
          this.moveBoard(this.articleNo);
        })
        .catch(() => {
          alert("글수정에 실패하였습니다.");
          this.moveList();
        });
    },
  },
};
</script>
<style scoped></style>
