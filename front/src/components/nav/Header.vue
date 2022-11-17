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
            ><router-link :to="{ name: 'list' }" class="nav-link"
              ><b-icon icon="building" font-scale="2"></b-icon>
              아파트매매정보</router-link
            ></b-nav-item
          >

          <!-- <c:if test="${userinfo.userRole eq 'admin' }">
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="${root}/user/list"
                >회원 관리</a
              >
            </li>
          </c:if> -->

          <!-- 로그인 전 -->
          <!-- <c:if test="${empty userinfo}">
            <ul class="navbar-nav me-2 mb-lg-0">
              <li class="nav-item">
                <a
                  class="nav-link"
                  id="btn-mv-join"
                  aria-current="page"
                  href="${root}/user/join"
                  >회원가입</a
                >
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  id="btn-mv-login"
                  aria-current="page"
                  href="${root}/user/login"
                  >로그인</a
                >
              </li>
            </ul>
          </c:if>
         로그인 후 
          <c:if test="${!empty userinfo}">
            <ul class="navbar-nav me-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link"
                  ><strong>${userinfo.userName}</strong>
                  (${userinfo.userId})님</a
                >
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  aria-current="page"
                  href="${root}/user/logout"
                  >로그아웃</a
                >
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  id="btn-info"
                  aria-current="page"
                  href="${root}/user/info"
                  >회원정보</a
                >
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  aria-current="page"
                  href="${root}/star/liststar"
                  >관심지역</a
                >
              </li>
            </ul>
          </c:if>
          -->
          <!-- </div> -->
          <!-- </div> -->
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
export default {
  name: "HeaderBar",
  data() {
    return {
      userinfo: {
        userName: "",
        userRole: "",
        userId: "",
      },
    };
  },
  created() {},
};
</script>
<style scoped></style>
