<template>
  <div ref="mapContainer" class="deckgl-map-container"></div>
</template>
  
  <script>
//import Deck from '@vue-deck.gl/core';
//import MapboxLayer from '@vue-deck.gl/mapbox';
import mapboxgl from "mapbox-gl";

export default {
  name: "DeckGLMap",
  data() {
    return {
      deckInstance: null,
    };
  },
  mounted() {
    this.initializeMap();
  },
  beforeUnmount() {
    if (this.deckInstance) {
      this.deckInstance.finalize();
    }
  },
  methods: {
    initializeMap() {
      mapboxgl.accessToken = import.meta.env.VITE_DECKGL_API_KEY;

      const map = new mapboxgl.Map({
        container: this.$refs.mapContainer,
        style: "mapbox://styles/mapbox/light-v9",
      });

      this.deckInstance = new Deck({
        canvas: this.$refs.mapContainer,
        initialViewState: {
          longitude: -122.41669,
          latitude: 37.7853, 
          zoom: 5,
        },
        controller: true,
      });

      map.on("load", () => {
        map.addLayer(
          new MapboxLayer({ id: "deckgl-layer", deck: this.deckInstance })
        );
      });
    },
  },
};
</script>
  
  <style>
.deckgl-map-container {
  width: 100%;
  height: 400px;
}
</style>
  