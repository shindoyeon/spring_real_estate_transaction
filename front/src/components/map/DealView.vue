<template>
  <div v-if="listVisible" id="showList" class="card p-0 bg-secondary">
    <!-- <b-icon icon="house" font-scale="2"></b-icon> -->
    <div
      class="text-center text-primary py-2 bg-primary"
      style="cursor: pointer"
    ></div>
    <!-- 아파트 정보 요약 -->
    <div class="bg-white mb-1">
      <div
        class="p-3 border-bottom d-flex justify-content-between align-items-center"
      >
        <h4 class="m-0">
          {{ houseList[curIndex].apartmentName }}
        </h4>
        <button
          @click="setBookmark"
          type="button"
          style="border: 0; outline: 0; background-color: white"
        >
          <b-icon
            icon="heart-fill"
            font-scale="1.5"
            variant="danger"
            v-if="isBookmark"
          ></b-icon>
          <b-icon
            icon="heart-fill"
            font-scale="1.5"
            variant="secondary"
            v-else
          ></b-icon>
        </button>
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
    <!-- 주변 인프라 버튼 -->
    <div class="bg-white mb-1 p-1 border-bottom d-flex align-items-center">
      <button
        @click="setInfraBookmark"
        type="button"
        class="btn btn-info m-2"
        style="font-size: 15px; padding: 5px 10px"
      >
        주변 인프라 정보
      </button>

      <!-- 원래 리뷰 버튼 -->
      <!-- @click="showReviewInsertModal" -->
      <!-- 새로 만든 모달 버틀 -->
      <!-- @click="showModal = true" -->
      <button
        type="button"
        class="btn btn-danger m-2"
        style="font-size: 15px; padding: 5px 10px"
        id="show-modal"
        @click="showReviewModal"
      >
        리뷰 등록
      </button>
    </div>

    <!-- 실거래가 -->
    <div class="bg-white mb-1">
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
    <!-- 거주민 리뷰 -->
    <div class="bg-white mb-1">
      <div v-if="reviewList.length == 0" class="p-3 border-top">
        <div>등록된 리뷰가 없습니다.</div>
      </div>
      <!-- 등록 리뷰 있을 때 v-for 속성 추가-->
      <div v-else v-for="(review, index) in reviewList" :key="index">
        <div class="border-top border-bottom d-flex align-items-center p-2">
          <div class="text-secondary ps-2 pe-3">
            <img
              class="avatar rounded-circle"
              width="25px"
              src="../../assets/images/profile_av.png"
            />
          </div>
          <div class="d-flex flex-column">
            <h6 class="m-0">{{ review.userId }}</h6>
          </div>
        </div>
        <div class="px-3">
          <div class="border-bottom d-flex py-2 text-danger">
            <div class="w-25">추천점수</div>
            <div>
              <StarRating
                v-model="review.rating"
                active-color="#dc3545"
                :read-only="true"
                :show-rating="false"
                :rounded-corners="true"
                :star-size="20"
                :star-points="[
                  23, 2, 14, 17, 0, 19, 10, 34, 7, 50, 23, 43, 38, 50, 36, 34,
                  46, 19, 31, 17,
                ]"
              ></StarRating>
            </div>
          </div>
          <div class="pt-2 text-secondary">종합의견</div>
          <div class="py-2">
            <h6>{{ review.content }}</h6>
          </div>
        </div>
      </div>
    </div>
    <review-modal v-if="showModal" @close="showModal = false">
      <!--
       you can use custom content here to overwrite
       default content
       -->
      <h3 slot="header">리뷰 등록</h3>
    </review-modal>
  </div>
</template>

<script>
import LineChart from "@/components/chart/LineChart.vue";
import { mapActions, mapMutations, mapState } from "vuex";
import StarRating from "vue-star-rating";
import ReviewModal from "@/components/review/ReviewModal.vue";
// import { kakaoApiInstance } from "@/api/kakao.js";
// import { apiInstance } from "@/api/index.js";

// const api = apiInstance();
// const kakaoHttp = kakaoApiInstance();
const storeName = "dealViewStore";

export default {
  name: "DealView",
  components: {
    LineChart,
    StarRating,
    ReviewModal,
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
      modalVisible: false,
      reviewInsertModal: null,
      showModal: false,
    };
  },
  // updated() {
  //   console.log("dealView updated");
  //   if (this.listVisible) console.log("모달 초기화");
  //   this.reviewInsertModal = new Modal(
  //     document.getElementById("reviewInsertModal")
  //   );
  // },
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
    ...mapState("kakaoStore", ["pharmsList", "subwaysList", "banksList"]),
    ...mapState("dealViewStore", ["infraTrigger"]),
    ...mapState("reviewStore", ["reviewList"]),
  },

  methods: {
    //actions
    ...mapActions(storeName, ["getHouseListByKeyword"]),
    ...mapActions("kakaoStore", ["getPharms", "getSubways", "getBanks"]),
    ...mapActions("bookmarkStore", [
      "insertBookmark",
      "getBookmarkList",
      "deleteBookmark",
    ]),

    //mutations
    ...mapMutations("dealViewStore", ["SET_INFRA_TRIGGER"]),
    ...mapMutations("kakaoStore", ["CLEAR_ALL_INFRA"]),
    ...mapMutations("bookmarkStore", ["SET_BOOKMARK_LIST", "SET_ISBOOKMARK"]),
    onKeywordSearch() {
      if (this.inputKeyword == "") {
        this.$swal("키워드를 입력하세요.", { icon: "error" });
      } else {
        this.eventFrom = "keyword";
        this.getHouseListByKeyword(this.inputKeyword);
      }
    },
    setBookmark() {
      if (!this.isLogin) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push({ name: "login" });
      } else {
        let param = {
          aptCode: this.houseList[this.curIndex].aptCode,
          userId: this.userInfo.userId,
        };
        if (!this.isBookmark) {
          this.insertBookmark(param);
        } else {
          this.deleteBookmark(param);
          console.log("북마크 삭제 완료");
        }
      }
    },
    async setInfraBookmark() {
      this.CLEAR_ALL_INFRA();
      let params = {
        x: this.houseList[this.curIndex].lng,
        y: this.houseList[this.curIndex].lat,
      };
      await this.getPharms(params);
      await this.getSubways(params);
      await this.getBanks(params);

      //인프라 마커생성 트리거 작동
      this.SET_INFRA_TRIGGER();
    },
    showReviewModal() {
      if (!this.isLogin) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push({ name: "login" });
      } else {
        this.showModal = true;
      }
    },
    // Modal 관련 메소드
    // showReviewInsertModal() {
    //   console.log("리뷰 모달 show () 호출");
    //   this.reviewInsertModal.show();
    // },
    // reviewInsertModalClose(houseNo) {
    //   this.getHouseReview(houseNo);
    //   this.reviewInsertModal.hide();
    // },
    // getHouseReview(aptCode) {
    //   console.log("getHouseReview 호출, aptcode:" + aptCode);
    // },
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
