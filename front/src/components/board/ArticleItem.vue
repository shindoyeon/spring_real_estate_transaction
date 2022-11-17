<template>
  <b-tr>
    <b-td v-text="article.articleNo"></b-td>
    <b-td
      v-text="article.subject"
      @click="showArticle(article.articleNo)"
    ></b-td>
    <b-td v-text="article.userId"></b-td>
    <b-td v-text="article.hit"></b-td>
    <b-td v-text="article.registerTime"></b-td>
    <!-- <b-td>{{ article.registerTime }}</b-td> -->
  </b-tr>
</template>
<script>
export default {
  name: "ArticleItem",
  props: {
    article: {},
    index: Number,
  },
  methods: {
    showArticle(no) {
      this.$router.push(`/board/detail/${no}`);
      // this.$route.push({ name: "detail", params: { no } });
    },
  },
};
</script>
<style scoped></style>
