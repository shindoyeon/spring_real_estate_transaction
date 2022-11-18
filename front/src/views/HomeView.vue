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
