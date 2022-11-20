import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "@/views/BoardView.vue";
import UserView from "@/views/UserView.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardModify from "@/components/board/BoardModify.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import UserMyPage from "@/components/user/UserMyPage.vue";
import UserFindPwd from "@/components/user/UserFindPwd.vue";
import UserRegister from "@/components/user/UserRegister.vue";
import LineChart from "@/components/chart/LineChart.vue";

import store from "@/store";

Vue.use(VueRouter);

const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/user",
    name: "user",
    component: UserView,
    children: [
      {
        path: "join",
        name: "join",
        component: UserRegister,
      },
      {
        path: "login",
        name: "login",
        component: UserLogin,
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: UserMyPage,
      },
      {
        path: "findpwd",
        name: "findpwd",
        component: UserFindPwd,
      },
    ],
  },
  {
    path: "/chart",
    name: "chart",
    component: LineChart,
  },
  {
    path: "/board",
    name: "board",
    component: BoardView,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "list",
        component: BoardList,
      },
      {
        path: "detail/:no",
        name: "detail",
        component: BoardDetail,
      },
      {
        path: "write",
        name: "write",
        component: BoardWrite,
      },
      {
        path: "modify/:no",
        name: "modify",
        component: BoardModify,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
