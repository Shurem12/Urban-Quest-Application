<template>
  <div class="google-map-container">
    <iframe
      v-if="mapUrl"
      :src="mapUrl"
      width="100%"
      height="450"
      style="border: 0"
      allowfullscreen
      aria-hidden="false"
      tabindex="0"
      title="Google Map"
    >
    </iframe>
  </div>
</template>

<script>
export default {
  props: {
    displayName: {
      type: String,
      default: "",
    },
    city: {
      type: String,
      default: "",
    },
  },
  computed: {
    mapUrl() {
      if (!this.displayName || !this.city) {
        return null;
      }
      const baseUrl = "https://www.google.com/maps/embed/v1/place";
      const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;
      const query = encodeURIComponent(`${this.displayName},${this.city}`);
      return `${baseUrl}?key=${apiKey}&q=${query}&zoom=18&maptype=satellite`;
    },
  },
};
</script>
