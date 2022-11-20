<template>
  <div>
    <!-- 상단 navbar start -->
    <b-navbar
      toggleable="lg"
      class="navbar navbar-expand-lg navbar-light bg-light shadow"
    >
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="navbar-nav me-auto mb-lg-0">
          <b-nav-item href="#"
            ><router-link
              :to="{ name: 'home' }"
              class="navbar-brand text-primary fw-bold"
              ><b-icon icon="house" font-scale="2"></b-icon> Where Is My
              Home</router-link
            ></b-nav-item
          >
          <b-nav-item href="#" class="nav-item"
            ><router-link :to="{ name: 'list' }" class="nav-link"
              ><b-icon icon="clipboard-check" font-scale="2"></b-icon>
              공지사항</router-link
            ></b-nav-item
          >
          <b-nav-item href="#" class="nav-item"
            ><router-link :to="{ name: 'chart' }" class="nav-link"
              ><b-icon icon="building" font-scale="2"></b-icon>
              아파트매매정보</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <!-- after login -->
        <b-navbar-nav class="ml-auto float-right" v-if="isLogin">
          <b-nav-item class="align-self-center">
            <b-avatar
              variant="primary"
              v-text="userInfo.userId.charAt(0).toUpperCase()"
            ></b-avatar>
            {{ userInfo.username }}({{ userInfo.userId }})님 환영합니다.
          </b-nav-item>
          <b-nav-item class="align-self-center">
            <router-link :to="{ name: 'mypage' }" class="link align-self-center"
              >내정보보기</router-link
            >
          </b-nav-item>
          <b-nav-item
            class="align-self-center link"
            @click.prevent="onClickLogout"
            >로그아웃</b-nav-item
          >
        </b-navbar-nav>
        <!-- before login -->
        <b-navbar-nav class="ml-auto float-right" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'join' }" class="link">
                <b-icon icon="person-circle"></b-icon> 회원가입
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'login' }" class="link">
                <b-icon icon="key"></b-icon> 로그인
              </router-link>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
import { mapState, mapGetters, mapActions, mapMutations } from "vuex";
const memberStore = "memberStore";

export default {
  name: "HeaderBar",
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userId);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>
<style scoped></style>
