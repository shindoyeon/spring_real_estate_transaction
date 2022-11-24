<template>
  <div id="searchBox" class="card">
    <div>
      <div class="p-2">
        <div class="d-flex">
          <b-icon icon="search"></b-icon>
          <h6 class="ps-2">검색 방법을 선택하세요</h6>
        </div>
        <div class="py-1 px-2 d-flex">
          <div class="ƒorm-check pe-3">
            <input
              value="D"
              v-model="searchType"
              class="form-check-input"
              type="radio"
              id="searchByDong"
            />
            <label class="form-check-label" for="searchByDong">동 검색</label>
          </div>
          <div class="form-check">
            <input
              value="K"
              v-model="searchType"
              class="form-check-input"
              type="radio"
              id="searchByKeyword"
            />
            <label class="form-check-label" for="searchByKeyword"
              >키워드 검색</label
            >
          </div>
        </div>
      </div>
      <div v-if="searchType == 'D'" class="pb-2 d-flex justify-content-evenly">
        <div class="col-lg-4 col-md-6 col-sm-6">
          <fieldset>
            <b-form-select
              v-model="sidoCode"
              :options="sidos"
              @change="gugunList"
              class="array-select form-control form-select"
              aria-label="example"
              >시</b-form-select
            >
          </fieldset>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
          <fieldset>
            <b-form-select
              v-model="gugunCode"
              :options="guguns"
              @change="dongList"
              class="array-select form-control form-select"
              aria-label="example"
              >구
            </b-form-select>
          </fieldset>
        </div>
        <div class="col-lg-4 col-md-6 col-sm-6">
          <fieldset>
            <b-form-select
              v-model="dongCode"
              :options="dongs"
              @change="onDongMenuChange"
              class="array-select form-control form-select"
              aria-label="example"
              >동
            </b-form-select>
          </fieldset>
        </div>
      </div>
      <div v-if="searchType == 'K'" class="input-group pb-2 px-3">
        <input
          @keypress.enter="onKeywordSearch"
          type="text"
          v-model="inputKeyword"
          class="form-control d-inline-block"
          placeholder="건물명 또는 동을 입력하세요"
        />
        <button
          @click="onKeywordSearch"
          class="btn btn-primary d-inline-block"
          type="button"
        >
          <b-icon icon="search"></b-icon>
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";
const dealViewStore = "dealViewStore";

export default {
  name: "SearchView",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      //
      map: "",
      searchType: "D",
      inputKeyword: "",
      markers: [],
      eventFrom: "",
      // curInfoWindow: {},
      infoWindows: [],
      curInfoWindow: null,
      curIndex: -1,
      dealInfo: [],
      reviewList: [],
      ongoingList: [],
      ongoingCount: 0,
      heart: true,
      reviewInsertModal: null,

      prevRoute: null,
    };
  },
  computed: {
    ...mapState(dealViewStore, [
      "sidos",
      "guguns",
      "dongs",
      "houseList",
      "listVisible",
    ]),
  },
  created() {
    this.initSearchByDongBox();

    if (this.fromMainKeyword) {
      console.log("yes");
      this.inputKeyword = this.fromMainKeyword;
      this.CLEAR_KEYWORD();
      this.searchType = "K";
      this.onKeywordSearch();
    }
  },
  watch: {
    searchType: function (val) {
      this.SET_LISTVISIBLE(false);
      if (val === "D") {
        console.log("watch D");
        this.initSearchByDongBox();
      } else if (val === "K") {
        if (this.listVisible) this.SET_LISTVISIBLE(false);
        console.log("watch K");
      }
    },
  },
  methods: {
    ...mapActions(dealViewStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseListByDong",
      "getHouseListByKeyword",
    ]),
    ...mapMutations(dealViewStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_HOUSE_LIST",
      "SET_LISTVISIBLE",
    ]),
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },

    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.sidoCode) this.getDong(this.gugunCode);
    },

    onDongMenuChange() {
      this.getHouseListByDong(this.dongCode);
    },

    initSearchByDongBox() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_HOUSE_LIST();
      this.getSido();
    },

    async onKeywordSearch() {
      if (this.inputKeyword == "") {
        this.$swal("키워드를 입력하세요.", { icon: "error" });
      } else {
        console.log("inputKeyword : " + this.inputKeyword);
        this.eventFrom = "keyword";
        await this.getHouseListByKeyword(this.inputKeyword);
      }
    },
  },
};
</script>
<style scoped>
#searchBox {
  position: absolute;

  top: 20px;
  /* bottom : 10px; */
  left: 20px;

  width: 400px;
  /* height: 400px; */
  padding: 10px;

  z-index: 100;
  /* background-color:rgba(255, 244, 244, 0.8); */
  /* opacity: 0.5; */
  background-color: rgba(255, 255, 255, 0.7);

  overflow-y: auto;
}
</style>
