<template>
  <div>
    <div id="map" style="width: 100%; height: 90vh"></div>
  </div>
</template>

<script>
export default {
  name: "MapView",

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
    console.log(this.prevRoute);
    console.log(this.prevRoute.name);
  },

  methods: {
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
  },
};
</script>

<style></style>
