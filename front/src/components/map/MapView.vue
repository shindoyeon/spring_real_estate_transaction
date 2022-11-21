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
import ImageSrc from "@/assets/images/house3.png";
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
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=014457ed3fb8a37eda241f4e7dc80c57&libraries=clusterer";

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
      const mapContainer = document.getElementById("map");

      const mapOption = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 6,
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
        let markerPosition = new kakao.maps.LatLng(lat, lng);
        this.addMarkerByOne(markerPosition, index);
        bounds.extend(markerPosition);
      });
      this.addInfoWindow();
      this.map.setBounds(bounds);
      // 마커 클러스터러를 생성합니다
      var clusterer = new kakao.maps.MarkerClusterer({
        map: this.map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 5, // 클러스터 할 최소 지도 레벨
        styles: [
          {
            width: "50px",
            height: "50px",
            background: "rgba(255, 51, 204,0.4)",
            borderRadius: "25px",
            color: "#000",
            textAlign: "center",
            fontWeight: "bold",
            lineHeight: "50px",
          },
        ],
      });

      clusterer.addMarkers(this.markers);
    },
    addMarkerByOne(markerPosition, index) {
      var imageSrc = ImageSrc, // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(30, 30), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOption
      );

      let marker = new kakao.maps.Marker({
        position: markerPosition,
        image: markerImage,
      });
      let $this = this;
      kakao.maps.event.addListener(marker, "click", function () {
        $this.showHouseDetail(index);
        // alert("인덱스:" + index);
      });
      this.markers.push(marker);
      marker.setMap(this.map);
      // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      var content =
        '<div class="customoverlay">' +
        '    <span class="title">구의</span>' +
        "</div>";

      // 커스텀 오버레이를 생성합니다
      var customOverlay = new kakao.maps.CustomOverlay({
        map: this.map,
        position: markerPosition,
        content: content,
        yAnchor: 1,
      });
      customOverlay.setMap(this.map);
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
.customoverlay {
  position: relative;
  bottom: 85px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
}
.customoverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.customoverlay a {
  display: block;
  text-decoration: none;
  color: #000;
  text-align: center;
  border-radius: 6px;
  font-size: 14px;
  font-weight: bold;
  overflow: hidden;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.customoverlay .title {
  display: block;
  text-align: center;
  background: #fff;
  padding: 10px 15px;
  font-size: 14px;
  font-weight: bold;
}
.customoverlay:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: -12px;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
</style>
