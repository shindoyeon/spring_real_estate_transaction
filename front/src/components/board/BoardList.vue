<template>
  <div>
    <b-container fluid="bv-example-row">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">공지사항</h2>
        </div>

        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2" style="padding: 1%">
            <div class="col-md-2 text-start">
              <b-button
                v-if="isLogin && userInfo.userRole == 1"
                variant="btn btn-outline-primary btn-sm"
                @click="moveWrite()"
                >글쓰기</b-button
              >
            </div>
            <div class="col-md-7 offset-3">
              <b-form class="d-flex">
                <input type="hidden" name="act" value="list" />
                <input type="hidden" name="no" value="1" />
                <b-form-select
                  class="form-select form-select-sm ms-5 me-1 w-50"
                  v-model="selected"
                  :options="options"
                ></b-form-select>
                <div class="input-group input-group-sm">
                  <b-form-input
                    v-model="searchText"
                    class="form-control"
                    placeholder="검색어..."
                  />
                  <b-button
                    id="btn-search"
                    class="btn btn-dark"
                    type="button"
                    @click="initComponent"
                  >
                    검색
                  </b-button>
                </div>
              </b-form>
            </div>
          </div>
          <b-table-simple striped hover small caption-top responsive>
            <colgroup>
              <col style="width: 5%" />
              <col style="width: 30%" />
              <col style="width: 25%" />
              <col style="width: 10%" />
              <col style="width: 30%" />
            </colgroup>
            <b-thead>
              <b-tr class="text-center">
                <b-th scope="col">글번호</b-th>
                <b-th scope="col">제목</b-th>
                <b-th scope="col">작성자</b-th>
                <b-th scope="col">조회수</b-th>
                <b-th scope="col">작성일</b-th>
              </b-tr>
            </b-thead>
            <b-tbody>
              <article-item
                v-for="(article, index) in articles"
                :no="article.no"
                :key="index"
                :article="article"
                :index="index"
              ></article-item>
            </b-tbody>
          </b-table-simple>
        </div>
        <page-link :currentPageIndex="currentPageIndex"></page-link>
      </div>
    </b-container>
  </div>
</template>

<script>
import { apiInstance } from "@/api/index.js";
import ArticleItem from "@/components/board/ArticleItem.vue";
import PageLink from "@/components/board/PageLink.vue";
import { mapState, mapGetters } from "vuex";
const memberStore = "memberStore";

const api = apiInstance();
export default {
  name: "BoardList",
  props: ["currentPageIndex"],
  components: {
    ArticleItem,
    PageLink,
  },
  data() {
    return {
      articles: [],
      subkey: "",
      word: "",
      searchText: "",
      selected: null,
      options: [
        { value: "null", text: "검색조건" },
        { value: "subject", text: "제목" },
        { value: "userId", text: "작성자" },
      ],
      pageLimit: 10,
      pageOffet: 0,
    };
  },
  created() {
    this.initComponent();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  watch: {
    "$route.query": function () {
      this.initComponent();
    },
  },
  methods: {
    initComponent() {
      if (this.$route.query.no == null) {
        console.log("init");
        this.offset = 0;
        this.pageLimit = 10;
        this.selected = null;
        this.searchText = "";
      } else {
        this.offset = `${this.$route.query.no - this.pageLimit}`;
      }
      api
        .get("/board/list", {
          params: {
            key: this.selected,
            word: this.searchText,
            limit: this.pageLimit,
            offset: this.offset,
          },
        })
        .then(({ data }) => {
          this.articles = data;
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },
    moveWrite() {
      this.$router.push(`/board/write`);
    },
  },
};
</script>
