<template>
  <div class="image-map-swap">
    <Button class="swap-button" @click="toggleView" :icon="currentViewIcon" />
    <div v-if="isMapView" class="google-map-container">
      <GoogleMap :displayName="displayName" :city="city" />
    </div>
    <div v-else class="galleria-container">
      <Galleria
        :showThumbnails="false"
        :showIndicators="true"
        :changeItemOnIndicatorHover="true"
        :showIndicatorsOnItem="inside"
        :indicatorsPosition="position"
        :value="galleriaImages"
        :responsiveOptions="responsiveOptions"
        :numVisible="5"
      >
        <template #item="slotProps">
          <img
            :src="getPhotoUrl(slotProps.item.name)"
            :alt="slotProps.item.alt"
            style="width: 100%"
          />
        </template>
        <template #thumbnail="slotProps">
          <img
            :src="getPhotoUrl(slotProps.item.name)"
            :alt="slotProps.item.alt"
          />
        </template>
      </Galleria>
    </div>
  </div>
</template>
  
  <script>
import { ref, computed } from "vue";
import Button from "primevue/button";
import GoogleMap from "./GoogleMap.vue";
import Galleria from "primevue/galleria";

export default {
  components: {
    Button,
    GoogleMap,
    Galleria,
  },
  props: {
    displayName: String,
    city: String,
    photos: Array,
  },
  setup(props) {
    const isMapView = ref(false); // Start with the image view by default
    const responsiveOptions = ref([
      // Add your responsive options if needed
    ]);

    const galleriaImages = computed(() => {
      return props.photos.map((photo) => {
        return {
          name: photo.name, // Store the photo name
          alt: `Photo of ${props.displayName}`,
        };
      });
    });

    function getPhotoUrl(name) {
      const apiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY; // Make sure to use your actual API key
      const maxWidthPx = 400; // Adjust as needed
      const maxHeightPx = 400; // Adjust as needed
      const url = `https://places.googleapis.com/v1/${name}/media?key=${apiKey}&maxHeightPx=${maxHeightPx}&maxWidthPx=${maxWidthPx}`;
      console.log("Fetching URL:", url); // Log the URL for debugging
      return url;
    }

    const toggleView = () => {
      isMapView.value = !isMapView.value;
    };

    const currentViewIcon = computed(() => {
      return isMapView.value ? "pi pi-map-marker" : "pi pi-images";
    });

    return {
      isMapView,
      galleriaImages,
      toggleView,
      currentViewIcon,
      responsiveOptions,
      getPhotoUrl,
    };
  },
};
</script>
  
  <style scoped>
.image-map-swap .swap-button {
  margin-bottom: 1rem;
}

.google-map-container {
  width: 100%;
  height: 450px;
  border-radius: 8px;
  overflow: hidden;
}
img {
  width: 100%;
  height: 450px;
  border-radius: 8px;
  overflow: hidden;
}
</style>
  