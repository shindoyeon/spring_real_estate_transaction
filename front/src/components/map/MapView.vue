<template>
  <div id="wrapper">
    <div id="map" style="width: 100%; height: 90vh"></div>
    <search-view />
    <deal-view />
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import DealView from "@/components/map/DealView.vue";
import SearchView from "@/components/map/SearchView.vue";
import { apiInstance } from "@/api/index.js";

const storeName = "dealViewStore";
const http = apiInstance();

export default {
  components: { DealView, SearchView },
  name: "MapView",

  data() {
    return {
      markers: [],
      map: "",
      curIndex: -1,
      dealInfo: [],
    };
  },

  component: {
    DealView,
    SearchView,
  },
  computed: {
    ...mapState(storeName, ["gu", "dong", "houseList", "fromMainKeyword"]),
  },
  watch: {
    houseList: function () {
      console.log("houseLIst change");
      this.removeMarkers();
      if (this.houseList.length) {
        this.addMarkers(this.houseList);
        console.log(this.houseList);
      } else {
        alert("정보가 없습니다.");
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
    ...mapActions(storeName, ["getHouseDeal"]),
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
      // this.addInfoWindow();
      this.map.setBounds(bounds);
    },
    addMarkerByOne(markerPosition, index) {
      let marker = new kakao.maps.Marker({
        position: markerPosition,
      });
      // let $this = this;
      kakao.maps.event.addListener(marker, "click", function () {
        // $this.showHouseDetail(index);
        alert("인덱스:" + index);
      });
      this.markers.push(marker);
      marker.setMap(this.map);
    },
    showHouseDetail(index) {
      this.curIndex = index;
      const houseNo = this.houseList[index].houseNo;
      this.getHouseDeal(houseNo);
      this.getOngoingList(houseNo);
      this.getHouseReview(houseNo);
      // if (!this.listVisible) this.listVisible = true;
    },
    getHouseDeal(houseNo) {
      http
        .get(`/house/deal/${houseNo}`)
        .then((response) => {
          this.dealInfo = response.data.houseDealDto;
        })
        .catch((error) => {
          // this.$swal("서버에 문제가 발생하였습니다.", { icon: "error" });
          alert("하나의 정보를 가져오는데 오류 발생:" + error);
        });
    },
  },
};
</script>

<style>
#wrapper {
  position: relative;
}
</style>
