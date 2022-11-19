<template>
  <div class="home">
    <map-view />
  </div>
</template>

<script>
// @ is an alias to /src
import MapView from "@/components/map/MapView.vue";

export default {
  name: "HomeView",
  components: {
    MapView,
  },
};
</script>
<style>
@import "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css";
@import "../assets/css/main.css";
</style>
