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
import PharmacySrc from "@/assets/images/pharmacy.png";
import BankSrc from "@/assets/images/bank.png";
import SubwaySrc from "@/assets/images/subway.png";
// import { apiInstance } from "@/api/index.js";

const storeName = "dealViewStore";
// const http = apiInstance();

export default {
  components: { DealView, SearchView },
  name: "MapView",

  data() {
    return {
      init: false,
      markers: [],
      pharmMarkers: [],
      subwayMarkers: [],
      bankMarkers: [],
      map: "",
      dealInfo: [],
      overlays: [],
      clusterer: null,
    };
  },
  mounted() {
    // kakao map 초기화
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => {
        kakao.maps.load(this.initMap);
      };

      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=014457ed3fb8a37eda241f4e7dc80c57&libraries=clusterer";

      document.head.appendChild(script);
    }
  },
  component: {
    DealView,
    SearchView,
  },
  watch: {
    houseList: function () {
      if (this.init && this.houseList.length == 0) {
        alert("조회된 매매내역이 없습니다.");
      }
      this.init = true;
      //클러스터 + 오버레이 초기화
      if (this.clusterer != null) {
        this.clusterer.clear();
        for (let i = 0; i < this.overlays.length; i++) {
          this.overlays[i].setMap(null);
        }
      }
      //마커들 초기화
      this.removeMarkers();
      this.removeInfraMarkers();
      this.SET_LISTVISIBLE(false);

      //마커들 추가
      if (this.houseList.length) {
        this.addMarkers(this.houseList);
      } else {
        // alert("정보가 없습니다.");
      }
    },
    infraTrigger: function () {
      console.log("인프라 트리거 바뀜!");
      this.removeInfraMarkers();
      this.addInfraMarkers(this.pharmsList, "pharms");
      this.addInfraMarkers(this.banksList, "bank");
      this.addInfraMarkers(this.subwaysList, "subway");
      this.addInfraInfoWindow();
    },
  },

  computed: {
    //스테이트
    ...mapState(storeName, [
      "gu",
      "dong",
      "houseList",
      "houseDealList",
      "fromMainKeyword",
      "curIndex",
      "listVisible",
      "infraTrigger",
    ]),
    ...mapState("bookmarkStore", ["bookmarkList"]),
    ...mapState("kakaoStore", ["pharmsList", "subwaysList", "banksList"]),
    ...mapState("reivewStore", ["reviewList"]),
  },
  methods: {
    //액션
    ...mapActions(storeName, ["getHouseDealList"]),
    ...mapActions("reviewStore", ["getReviewList"]),
    //뮤테이션
    ...mapMutations(storeName, ["SET_CURINDEX", "SET_LISTVISIBLE"]),
    ...mapMutations("bookmarkStore", ["SET_ISBOOKMARK"]),
    initMap() {
      const mapContainer = document.getElementById("map");

      const mapOption = {
        center: new kakao.maps.LatLng(37.5012743, 127.039585),
        level: 6,
      };
      this.map = new kakao.maps.Map(mapContainer, mapOption);
      var map = this.map;
      // // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      // var zoomControl = new kakao.maps.ZoomControl();
      // map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

      // 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
      kakao.maps.event.addListener(map, "zoom_changed", () => {
        // 지도의 현재 레벨을 얻어옵니다
        // var level = map.getLevel();
        // var message = "현재 지도 레벨은 " + level + " 입니다";
        // console.log(message);
        this.setOverlay();
      });
    },
    setOverlay() {
      var level = this.map.getLevel();
      console.log("현재 지도의 레벨은 " + level + "입니다");
      if (level > 6) {
        let i;
        for (i = 0; i < this.overlays.length; i++) {
          this.overlays[i].setVisible(false);
        }
      }
      if (level <= 6) {
        let i;
        for (i = 0; i < this.overlays.length; i++) {
          this.overlays[i].setVisible(true);
        }
      }
    },
    //지도 관련 메소드
    removeMarkers() {
      // if (this.curInfoWindow) {
      //   this.curInfoWindow.close();
      //   this.curInfoWindow = null;
      // }

      // this.infoWindows.forEach(i => i.close());
      // this.infoWindows = [];
      this.markers.forEach((m) => {
        m.setMap(null);
      });
      this.markers = [];
    },
    removeInfraMarkers() {
      this.pharmMarkers.forEach((m) => m.setMap(null));
      this.pharmMarkers = [];
      this.subwayMarkers.forEach((m) => m.setMap(null));
      this.subwayMarkers = [];
      this.bankMarkers.forEach((m) => m.setMap(null));
      this.bankMarkers = [];
    },
    //아파트 마커
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
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 6, // 클러스터 할 최소 지도 레벨
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
      this.clusterer.addMarkers(this.markers);
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
      let item = this.houseList[index];
      let min = item.minAmount / 10000;
      let max = item.maxAmount / 10000;
      var content =
        `<div class="customoverlay">` +
        `    <span class="title">${min}억~<br>${max}억</span>` +
        `</div>`;

      // 커스텀 오버레이를 생성합니다
      var customOverlay = new kakao.maps.CustomOverlay({
        map: this.map,
        position: markerPosition,
        content: content,
        yAnchor: 1,
      });

      kakao.maps.event.addListener(customOverlay, "mouseover", function () {
        customOverlay.parent("div").css("z-index", "100");
      });

      kakao.maps.event.addListener(customOverlay, "mouseleave", function () {
        customOverlay.parent("div").css("z-index", "1");
      });
      this.overlays.push(customOverlay);
      customOverlay.setMap(this.map);
    },
    addInfoWindow() {
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
          infoWindow.open($this.map, marker);
          // $this.curInfoWindow = infoWindow;
        });
        kakao.maps.event.addListener(marker, "mouseout", function () {
          infoWindow.close();
        });
      });
    },
    //인프라 마커
    addInfraMarkers(list, infra) {
      let bounds = new kakao.maps.LatLngBounds();

      list.forEach(({ y, x }) => {
        let markerPosition = new kakao.maps.LatLng(y, x);
        this.addInfraMarkerByOne(markerPosition, infra);
        bounds.extend(markerPosition);
      });
      this.map.setBounds(bounds);
    },
    addInfraMarkerByOne(markerPosition, infra) {
      var infrasrc;
      if (infra == "pharms") {
        infrasrc = PharmacySrc;
      } else if (infra == "bank") {
        infrasrc = BankSrc;
      } else if (infra == "subway") {
        infrasrc = SubwaySrc;
      }
      var imageSrc = infrasrc, // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(25, 25), // 마커이미지의 크기입니다
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

      if (infra == "pharms") {
        this.pharmMarkers.push(marker);
      } else if (infra == "subway") {
        this.subwayMarkers.push(marker);
      } else if (infra == "bank") {
        this.bankMarkers.push(marker);
      }
      marker.setMap(this.map);
    },
    addInfraInfoWindow() {
      //약국 인포윈도우
      this.pharmMarkers.forEach((marker, index) => {
        let item = this.pharmsList[index];
        let infoContents = `<div style="width:150px;text-align:center;padding:6px 0;">${item.place_name}</div>`;

        let infoWindow = new kakao.maps.InfoWindow({
          content: infoContents,
        });
        // infoWindow.open(this.map, marker);
        // this.infoWindows.push(infoWindow);
        let $this = this;
        kakao.maps.event.addListener(marker, "mouseover", function () {
          infoWindow.open($this.map, marker);
          // $this.curInfoWindow = infoWindow;
        });
        kakao.maps.event.addListener(marker, "mouseout", function () {
          infoWindow.close();
        });
      });
      //지하철 인포윈도우
      this.subwayMarkers.forEach((marker, index) => {
        let item = this.subwaysList[index];
        let infoContents = `<div style="width:150px;text-align:center;padding:6px 0;">${item.place_name}</div>`;

        let infoWindow = new kakao.maps.InfoWindow({
          content: infoContents,
        });
        // infoWindow.open(this.map, marker);
        // this.infoWindows.push(infoWindow);
        let $this = this;
        kakao.maps.event.addListener(marker, "mouseover", function () {
          infoWindow.open($this.map, marker);
          // $this.curInfoWindow = infoWindow;
        });
        kakao.maps.event.addListener(marker, "mouseout", function () {
          infoWindow.close();
        });
      });
      //은행 인포윈도우
      this.bankMarkers.forEach((marker, index) => {
        let item = this.banksList[index];
        let infoContents = `<div style="width:150px;text-align:center;padding:6px 0;">${item.place_name}</div>`;

        let infoWindow = new kakao.maps.InfoWindow({
          content: infoContents,
        });
        // infoWindow.open(this.map, marker);
        // this.infoWindows.push(infoWindow);
        let $this = this;
        kakao.maps.event.addListener(marker, "mouseover", function () {
          infoWindow.open($this.map, marker);
          // $this.curInfoWindow = infoWindow;
        });
        kakao.maps.event.addListener(marker, "mouseout", function () {
          infoWindow.close();
        });
      });
    },

    showHouseDetail(index) {
      this.removeInfraMarkers();
      if (document.getElementById("showList")) {
        document.getElementById("showList").scrollTop = 0;
      }
      this.SET_CURINDEX(index);

      const houseNo = this.houseList[index].aptCode;
      this.getHouseDealList(houseNo);
      this.getReviewList(houseNo);
      if (!this.listVisible) this.SET_LISTVISIBLE(true);

      let bookchk = false;
      this.bookmarkList.forEach((bookmark) => {
        let code = this.houseList[this.curIndex].aptCode;
        if (code == bookmark.aptCode) {
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
  bottom: 75px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  /* float: left; */
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
  background: #e59226;
  color: #fff;
  padding: 2px 6px;
  font-size: 12px;
  font-weight: bold;
}
.customoverlay:after {
  content: "";
  position: absolute;
  margin-left: -10px;
  left: 50%;
  bottom: -12px;
  width: 20px;
  height: 10px;
  /* background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png"); */
}
</style>
