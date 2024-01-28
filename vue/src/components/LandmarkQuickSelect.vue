<template>
  <div class="landmark-quick-select">
    <Dropdown
      v-model="selectedLandmark"
      :options="landmarks"
      optionLabel="name"
      placeholder="Select a Starting Point"
      :showClear="true"
      :filter="true"
      filterBy="name"
      :filterPlaceholder="filterPlaceholder"
      :disabled="!city"
    />
  </div>
</template>
  
  
  <script>
import { ref, watch } from "vue";
import Dropdown from "primevue/dropdown";
import LandmarkService from "../services/LandmarkService";

export default {
  components: {
    Dropdown,
  },
  props: {
    city: String,
    initialValue: Object,
  },
  emits: ["update:modelValue", "error"],
  setup(props, { emit }) {
    const landmarks = ref([]);
    const selectedLandmark = ref(props.initialValue || null);
    const filterPlaceholder = "Type to search...";

    // Initial fetch if city is already set
    if (props.city) {
      fetchLandmarks(props.city);
    }

    // Fetch landmarks whenever the city prop changes
    watch(() => props.city, (newCity) => {
      if (newCity) {
        fetchLandmarks(newCity);
      } else {
        landmarks.value = []; // Reset landmarks when there is no city
      }
    });

    const fetchLandmarks = async (cityName) => {
      try {
        const response = await LandmarkService.returnLandmarks(cityName);
        landmarks.value = response.data.map((landmark) => ({
          name: landmark.displayName?.text,
          value: landmark.id,
        }));
      } catch (error) {
        console.error("Error fetching landmarks:", error);
        emit("error", "Unable to fetch landmarks");
      }
    };

    // Emit the selected landmark's value when it changes
    watch(() => selectedLandmark.value, (newValue) => {
      emit("update:modelValue", newValue ? newValue.value : null);
    });

    return {
      landmarks,
      selectedLandmark,
      filterPlaceholder,
    };
  },
};
</script>
  