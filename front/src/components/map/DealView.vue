<template>
  <div v-if="listVisible" id="showList" class="card p-0 bg-secondary">
    <!-- <b-icon icon="house" font-scale="2"></b-icon> -->
    <div
      v-if="salechk"
      class="text-center text-white py-2"
      style="background-color: #ccc"
    >
      <h6>매물 보기 (0개)</h6>
    </div>
    <div
      v-else
      class="text-center text-primary py-2 bg-primary"
      style="cursor: pointer"
    ></div>
    <!-- 아파트 정보 요약 -->
    <div class="bg-white mb-2">
      <div
        class="p-3 border-bottom d-flex justify-content-between align-items-center"
      >
        <h4 class="m-0">{{ houseList[curIndex].apartmentName }}</h4>
        <button
          @click="setBookmark"
          type="button"
          style="border: 0; outline: 0; background-color: white"
        >
          <b-icon
            icon="heart-fill"
            font-scale="2"
            variant="danger"
            v-if="isBookmark"
          ></b-icon>
          <b-icon
            icon="heart-fill"
            font-scale="2"
            variant="secondary"
            v-else
          ></b-icon>
        </button>
        <!-- contents -->
      </div>
      <div class="px-3">
        <div class="border-bottom d-flex py-2">
          <div class="text-secondary w-25">주소</div>
          {{ houseList[curIndex].dong }}
          {{ houseList[curIndex].jibun }}
        </div>
        <div class="d-flex py-2">
          <div class="text-secondary w-25">건축년도</div>
          <div>{{ houseList[curIndex].buildYear }}</div>
        </div>
      </div>
    </div>
    <!-- 거주민 리뷰 -->
    <!-- <div class="bg-white mb-2">
      <div class="d-flex justify-content-between align-items-center">
        <h5 class="p-3 m-0">거주민 리뷰</h5>

        <b-icon icon="plus-circle" font-scale="2"></b-icon>
      </div>
      <div v-if="reviewchk" class="p-3 border-top">
        <div>등록된 리뷰가 없습니다.</div>
      </div>
      <div v-else>
        <div class="border-top border-bottom d-flex align-items-center p-2">
          <div class="text-secondary ps-2 pe-3">
            <b-icon v-if="profilechk" icon="person" font-scale="2"></b-icon>
          </div>
          <div class="d-flex flex-column">
            <h6 class="m-0">신동희</h6>
            <div class="text-secondary" style="font-size: 0.9rem">
              2022.11.17 가입
            </div>
          </div>
        </div>
        <div class="px-3">
          <div class="border-bottom d-flex py-2 text-danger">
            <div class="w-25">추천점수</div>
            <div>5</div>
          </div>
          <div class="border-bottom d-flex py-2">
            <div class="text-secondary w-25">교통요건</div>
            <div>5</div>
          </div>
          <div class="border-bottom d-flex py-2">
            <div class="text-secondary w-25">거주환경</div>
            <div>5</div>
          </div>
          <div class="border-bottom d-flex py-2">
            <div class="text-secondary w-25">주변환경</div>
            <div>5</div>
          </div>
          <div class="pt-2 text-secondary">종합의견</div>
          <div class="py-2">
            <h6>낫배두</h6>
          </div>
        </div>
      </div>
    </div> -->
    <!-- 실거래가 -->
    <div class="bg-white mb-2">
      <div class="border-bottom"><h5 class="p-3 m-0">실거래가</h5></div>
      <div>
        <line-chart />
        <table class="w-100">
          <thead class="bg-secondary text-white">
            <tr>
              <td class="ps-3 py-1">거래일</td>
              <td>거래가격</td>
              <td>면적</td>
              <td>층수</td>
            </tr>
          </thead>
          <tbody class="px-2" v-if="houseDealList <= 10">
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
          <tbody class="px-2" v-else>
            <tr
              v-for="(item, index) in houseDealList"
              :key="index"
              class="border-bottom"
              v-show="index >= houseDealList.length - 10"
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
    <!-- 매물정보 -->
    <!-- <div class="bg-white mb-2">
      <div class="border-bottom"><h5 class="p-3 m-0">매물 정보</h5></div>
      <div>
        <table class="w-100">
          <thead class="bg-secondary text-white">
            <tr>
              <td class="ps-3 py-1">타입</td>
              <td class="w-50">제목</td>
              <td>거래가격</td>
              <td></td>
            </tr>
          </thead>
          <tbody class="px-2">
            <tr v-if="dealchk" class="border-bottom">
              <td colspan="3" class="ps-3 py-2">등록된 매물이 없습니다.</td>
            </tr>
            <tr v-else v-for="index in 5" :key="index" class="border-bottom">
              <td class="ps-3 py-2">빌딩</td>
              <td>제목</td>
              <td>1억</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div> -->
  </div>
</template>

<script>
import LineChart from "@/components/chart/LineChart.vue";
import { mapActions, mapMutations, mapState } from "vuex";
// import { apiInstance } from "@/api/index.js";

// const api = apiInstance();
const storeName = "dealViewStore";

export default {
  name: "DealView",
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
    };
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
    ...mapState("bookmarkStore", ["bookmarkList", "isBookmark"]),
  },

  methods: {
    ...mapActions(storeName, ["getHouseListByKeyword"]),
    ...mapActions("bookmarkStore", [
      "insertBookmark",
      "getBookmarkList",
      "deleteBookmark",
    ]),
    ...mapMutations("bookmarkStore", ["SET_BOOKMARK_LIST", "SET_ISBOOKMARK"]),
    onKeywordSearch() {
      if (this.inputKeyword == "") {
        this.$swal("키워드를 입력하세요.", { icon: "error" });
      } else {
        console.log("inputKeyword : " + this.inputKeyword);
        this.eventFrom = "keyword";
        this.getHouseListByKeyword(this.inputKeyword);
      }
    },
    setBookmark() {
      let param = {
        aptCode: this.houseList[this.curIndex].aptCode,
        userId: this.userInfo.userId,
      };
      if (!this.isLogin) {
        alert("로그인이 필요한 서비스입니다.");
      } else {
        if (!this.isBookmark) {
          console.log(param);
          this.insertBookmark(param);
          console.log("bookmarkList:" + this.bookmarkList);
        } else {
          this.deleteBookmark(param);
          console.log("북마크 삭제 완료");
        }
      }
    },
  },
};
</script>

<style>
#showList {
  position: absolute;

  top: 20px;
  bottom: 20px;
  right: 20px;

  width: 400px;
  padding: 10px;

  z-index: 100;
  /* background-color:rgba(255, 244, 244, 0.8); */
  /* opacity: 0.5; */

  overflow-y: auto;
}
</style>
