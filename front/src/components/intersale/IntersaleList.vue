<template>
  <div>
    <!-- 관심목록 리스트 -->
    <div class="wrap" v-if="myBookmarkList.length > 0">
      <div
        class="bg-white"
        v-for="(item, index) in myBookmarkList"
        :key="index"
        id="intersale"
      >
        <!-- 관심 매물 정보 -->
        <div style="margin-bottom: 20px">
          <button
            @click="showDetail(item.aptCode)"
            type="button"
            style="border: 0; outline: 0; background-color: white; float: left"
          >
            <h5 class="m-0" style="text-align: left">
              {{ item.apartmentName }}
            </h5>
          </button>
          <button
            @click="setBookmarkOne(item.aptCode)"
            type="button"
            style="border: 0; outline: 0; background-color: white"
          >
            <b-icon icon="heart-fill" scale="1.5" variant="danger"></b-icon>
          </button>
          <!-- contents -->
        </div>
        <div class="px-2">
          <div class="d-flex py-1">
            <div class="text-secondary w-25" style="text-align: left">주소</div>
            <div style="margin-left: 3">{{ item.dong }} {{ item.jibun }}</div>
          </div>
          <div class="d-flex py-1">
            <div class="text-secondary w-25" style="text-align: left">
              건축년도
            </div>
            <div style="margin-left: 3">
              {{ item.buildYear }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="wrap" v-else>관심매물이 없습니다.</div>
    <div class="container">
      <b-row>
        <h5 class="my-4 py-2 shadow-sm bg-light text-center bg-white">
          관심매물 상세내역
        </h5>

        <!-- <div class="d-flex justify-content-end"> -->
        <!-- 관심매물 실거래가 리스트 -->
        <div
          id="dealList"
          class="card p-0 bg-secondary flex"
          v-if="saleVisible"
        >
          <!-- <b-icon icon="house" font-scale="2"></b-icon> -->
          <div
            class="text-center text-primary py-2 bg-primary"
            style="cursor: pointer"
          ></div>
          <!-- 실거래가 -->
          <div class="bg-white mb-2">
            <div class="border-bottom">
              <h5 class="p-3 m-0">실거래가</h5>
            </div>
            <div>
              <table class="w-100">
                <thead class="bg-secondary text-white">
                  <tr>
                    <td class="ps-3 py-1">거래일</td>
                    <td>거래가격</td>
                    <td>면적</td>
                    <td>층수</td>
                  </tr>
                </thead>
                <tbody class="px-2">
                  <tr
                    v-for="(item, index) in houseDealList"
                    :key="index"
                    class="border-bottom"
                  >
                    <td class="ps-3 py-2">{{ item.dealYear }}</td>
                    <td>{{ item.dealAmount }}</td>
                    <td>{{ item.area }}</td>
                    <td>{{ item.floor }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <!-- 실거래가 차트 -->
        <div id="chart" v-if="saleVisible"><line-chart /></div>
        <!-- </div> -->
      </b-row>
    </div>
  </div>
</template>

<script>
import LineChart from "@/components/chart/LineChart.vue";
import { mapActions, mapMutations, mapState } from "vuex";
// import { apiInstance } from "@/api/index.js";

// const api = apiInstance();
const storeName = "dealViewStore";

export default {
  name: "BookmarkInfoList",
  components: {
    LineChart,
  },
  data() {
    return {
      salechk: false,
      reviewchk: false,
      profilechk: true,
      dealchk: false,
      inputKeyword: "",
      eventFrom: "",
      bookmark: "",
      saleVisible: false,
    };
  },
  created() {
    let aptCodes = [];
    this.bookmarkList.forEach((element) => {
      aptCodes.push(element.aptCode);
    });
    this.getMyBookmarkList(aptCodes);
  },
  computed: {
    ...mapState(storeName, [
      "gu",
      "dong",
      "houseList",
      "houseDealList",
      "fromMainKeyword",
      "curIndex",
      "listVisible",
    ]),
    ...mapState("memberStore", ["isLogin", "userInfo"]),
    ...mapState("bookmarkStore", [
      "bookmarkList",
      "isBookmark",
      "myBookmarkList",
    ]),
  },

  methods: {
    ...mapActions(storeName, ["getHouseListByKeyword", "getHouseDealList"]),
    ...mapActions("bookmarkStore", [
      "insertBookmark",
      "getBookmarkList",
      "deleteBookmark",
      "getMyBookmarkList",
    ]),
    ...mapMutations("bookmarkStore", ["SET_BOOKMARK_LIST", "SET_ISBOOKMARK"]),
    // setBookmark() {
    //   let param = {
    //     aptCode: this.houseList[this.curIndex].aptCode,
    //     userId: this.userInfo.userId,
    //   };
    //   if (!this.isLogin) {
    //     alert("로그인이 필요한 서비스입니다.");
    //   } else {
    //     if (!this.isBookmark) {
    //       console.log(param);
    //       this.insertBookmark(param);
    //       console.log("bookmarkList:" + this.bookmarkList);
    //     } else {
    //       this.deleteBookmark(param);
    //       console.log("북마크 삭제 완료");
    //     }
    //   }
    // },
    async setBookmarkOne(thisAptCode) {
      let param = {
        aptCode: thisAptCode,
        userId: this.userInfo.userId,
      };
      if (!this.isLogin) {
        alert("로그인이 필요한 서비스입니다.");
      } else {
        await this.deleteBookmark(param);
        let codes = [];
        this.bookmarkList.forEach((element) => {
          codes.push(element.aptCode);
        });
        console.log("codes: " + codes);
        await this.getMyBookmarkList(codes);
        this.saleVisible = false;
        console.log("북마크 삭제 완료");
      }
    },
    showDetail(aptCode) {
      this.saleVisible = true;
      this.getHouseDealList(aptCode);
    },
    // hasBookmark(code) {
    //   this.myBookmarkList.forEach((element) => {
    //     if (code == element.aptCode) {
    //       return true;
    //     }
    //   });

    //   return false;
    // },
  },
};
</script>
<style scoped>
.wrap {
  width: 80%;
  height: 160px;
  overflow-x: scroll;
  white-space: nowrap;
  margin: auto;
  display: flex;
  margin-top: 50px;
}

.wrap div {
  width: 350px;
}

#intersale {
  height: 100px;
}

#dealList {
  position: absolute;
  top: 300px;
  /* bottom: 20px; */
  left: 200px;
  width: 400px;
  height: 550px;
  padding: 30px;
  margin-top: 5%;
  z-index: 100;
  /* background-color:rgba(255, 244, 244, 0.8); */
  /* opacity: 0.5; */

  overflow-y: auto;
}

#chart {
  position: absolute;
  top: 300px;
  bottom: 20px;
  left: 650px;
  width: 800px;
  height: 600px;
  margin-top: 5%;
  text-align: center;
}
</style>
