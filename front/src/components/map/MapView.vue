<template>
  <div id="wrapper">
    <div id="map" style="width: 100%; height: 90vh"></div>
    <search-view />
    <deal-view />
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import DealView from "@/components/map/DealView.vue";
import SearchView from "@/components/map/SearchView.vue";
// import { apiInstance } from "@/api/index.js";

const storeName = "dealViewStore";
// const http = apiInstance();

export default {
  components: { DealView, SearchView },
  name: "MapView",

  data() {
    return {
      markers: [],
      map: "",
      dealInfo: [],
    };
  },

  component: {
    DealView,
    SearchView,
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
    ...mapState("bookmarkStore", ["bookmarkList"]),
  },
  watch: {
    houseList: function () {
      console.log("houseLIst change");
      this.removeMarkers();
      this.SET_LISTVISIBLE(false);
      if (this.houseList.length) {
        this.addMarkers(this.houseList);
        console.log(this.houseList);
      } else {
        // alert("정보가 없습니다.");
      }
    },
  },

  mounted() {
    // kakao map 초기화
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=014457ed3fb8a37eda241f4e7dc80c57";

      document.head.appendChild(script);
    }
    // appkey 앞에 autoload=false&
    // console.log(this.prevRoute);
    // console.log(this.prevRoute.name);
  },

  methods: {
    ...mapActions(storeName, ["getHouseDealList"]),
    ...mapMutations(storeName, ["SET_CURINDEX", "SET_LISTVISIBLE"]),
    ...mapMutations("bookmarkStore", ["SET_ISBOOKMARK"]),
    initMap() {
      console.log("initMap");
      const mapContainer = document.getElementById("map");
      console.log(mapContainer);

      const mapOption = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 4,
      };
      console.log(mapOption);
      this.map = new kakao.maps.Map(mapContainer, mapOption);
    },

    //지도 관련 메소드
    removeMarkers() {
      // if (this.curInfoWindow) {
      //   this.curInfoWindow.close();
      //   this.curInfoWindow = null;
      // }

      // this.infoWindows.forEach(i => i.close());
      // this.infoWindows = [];
      this.markers.forEach((m) => m.setMap(null));
      this.markers = [];
    },
    addMarkers(list) {
      let bounds = new kakao.maps.LatLngBounds();

      list.forEach(({ lat, lng }, index) => {
        // console.log(`forEach ${index}`);
        let markerPosition = new kakao.maps.LatLng(lat, lng);
        this.addMarkerByOne(markerPosition, index);
        bounds.extend(markerPosition);
      });
      this.addInfoWindow();
      this.map.setBounds(bounds);
    },
    addMarkerByOne(markerPosition, index) {
      let marker = new kakao.maps.Marker({
        position: markerPosition,
      });
      let $this = this;
      kakao.maps.event.addListener(marker, "click", function () {
        $this.showHouseDetail(index);
        // alert("인덱스:" + index);
      });
      this.markers.push(marker);
      marker.setMap(this.map);
    },
    addInfoWindow() {
      console.log("addiw");
      this.markers.forEach((marker, index) => {
        let item = this.houseList[index];
        let infoContents = `<div style="width:150px;text-align:center;padding:6px 0;">${item.apartmentName}</div>`;

        let infoWindow = new kakao.maps.InfoWindow({
          content: infoContents,
        });
        // infoWindow.open(this.map, marker);
        // this.infoWindows.push(infoWindow);
        let $this = this;
        kakao.maps.event.addListener(marker, "mouseover", function () {
          // console.log(1)
          // console.log(this.curInfoWindow)
          // if ($this.curInfoWindow) {
          //   if ($this.curInfoWindow.name != item.aptName) {
          //     $this.curInfoWindow.close();
          //     infoWindow.open(this.map, marker);
          //   }
          // } else {
          //   console.log('ttt')
          infoWindow.open($this.map, marker);
          // $this.curInfoWindow = infoWindow;
        });
        kakao.maps.event.addListener(marker, "mouseout", function () {
          infoWindow.close();
        });
      });
    },
    showHouseDetail(index) {
      if (document.getElementById("showList")) {
        document.getElementById("showList").scrollTop = 0;
      }
      this.SET_CURINDEX(index);

      const houseNo = this.houseList[index].aptCode;
      console.log(houseNo);
      this.getHouseDealList(houseNo);
      // this.getOngoingList(houseNo);
      // this.getHouseReview(houseNo);
      if (!this.listVisible) this.SET_LISTVISIBLE(true);

      let bookchk = false;
      this.bookmarkList.forEach((bookmark) => {
        let code = this.houseList[this.curIndex].aptCode;
        console.log("code : " + code);
        if (code == bookmark.aptCode) {
          console.log("찾았음");
          bookchk = true;
        }
      });
      this.SET_ISBOOKMARK(bookchk);
    },

    // getHouseDeal(houseNo) {
    //   http
    //     .get(`/house/deal/${houseNo}`)
    //     .then((response) => {
    //       this.dealInfo = response.data.houseDealDto;
    //     })
    //     .catch((error) => {
    //       // this.$swal("서버에 문제가 발생하였습니다.", { icon: "error" });
    //       alert("하나의 정보를 가져오는데 오류 발생:" + error);
    //     });
    // },
  },
};
</script>

<style>
#wrapper {
  position: relative;
}
</style>
