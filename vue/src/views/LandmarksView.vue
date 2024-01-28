<template>
  <div class="landmarks-container">
    <h1>Sphinx Tours</h1>
    <h2>Explore Landmarks</h2>
    <SearchHeader v-model="selectedCity" />
    <div id="itinerary-select-area">
      <Dropdown
        :options="itineraries"
        optionLabel="name"
        v-model="selectedItineraryId"
        @change="handleItinerarySelection"
        placeholder="Select an Itinerary"
      />
      <Button
        label="Add To Itinerary"
        class="add-itinerary-btn"
        @click="addToItinerary"
      ></Button>
    </div>
    <Accordion>
      <AccordionTab v-for="landmark in landmarks" :key="landmark.id" class="landmark-tab">
        <p id="select-checkmark-area">
          <strong>Select&nbsp;&nbsp;</strong
          ><Checkbox :value="landmark.id" v-model="selectedLandmarks" />
        </p>
        <template #header>
          <div class="accordion-header">
            <span>{{ landmark.displayName?.text }}</span>
          </div>
        </template>
        <div class="landmark-details">
          <div class="landmark-info">
            <h2>{{ landmark.displayName?.text }}</h2>
            <p v-if="landmark.formattedAddress">
              <strong>Address:</strong> {{ landmark.formattedAddress }}
            </p>
            <p v-if="landmark.types">
              <strong>Types:</strong> {{ landmark.types.join(", ") }}
            </p>
            <p v-if="landmark.accessibilityOptions">
              <strong>Accessibility Options:</strong>
              {{ formatAccessibilityOptions(landmark.accessibilityOptions) }}
            </p>
          </div>
          <div class="landmark-media">
            <ImageMapSwap
              v-if="
                landmark.photos.length > 0 ||
                (landmark.location &&
                  landmark.location.latitude &&
                  landmark.location.longitude)
              "
              :photos="landmark.photos"
              :displayName="landmark.displayName?.text"
              :city="selectedCity.value"
            />
            <div
              v-if="
                landmark.currentOpeningHours &&
                landmark.currentOpeningHours.weekdayDescriptions
              "
              class="hours-table"
            >
              <h3>Opening Hours</h3>
              <DataTable :value="formatOpeningHours(landmark)">
                <Column field="day" header="Day" sortable></Column>
                <Column field="hours" header="Hours" sortable></Column>
              </DataTable>
            </div>
          </div>
        </div>
      </AccordionTab>
    </Accordion>
    <Toast ref="toast" />
  </div>
</template>

<script>
import Checkbox from "primevue/checkbox";
import { ref, computed, onMounted, watch } from "vue";
import Accordion from "primevue/accordion";
import AccordionTab from "primevue/accordiontab";
import Dropdown from "primevue/dropdown";
import LandmarkService from "../services/LandmarkService";
import SearchHeader from "../components/SearchHeader.vue";
import Button from "primevue/button";
import ImageMapSwap from "../components/ImageMapSwap.vue";
import ItineraryService from "../services/ItineraryService";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Toast from 'primevue/toast';
import ToastService from 'primevue/toastservice';

export default {
  components: {
    ImageMapSwap,
    Accordion,
    AccordionTab,
    SearchHeader,
    Checkbox,
    Button,
    Dropdown,
    DataTable,
    Column,
    Toast,
    ToastService
  },
  setup() {
    const selectedCity = ref();
    const landmarks = ref([]);
    const selectedLandmarks = ref([]);
    const selectedItineraryId = ref();
    const itineraries = ref([]);

    const fetchItineraries = async () => {
      try {
        const response = await ItineraryService.returnAllItinerary();
        itineraries.value = response.data;
      } catch (error) {
        console.error("Error fetching itineraries:", error);
      }
    };

    const addToItinerary = async () => {
      if (!selectedItineraryId.value || typeof selectedItineraryId.value.itineraryId !== 'number') {
        toast.add({ severity: 'error', summary: 'Error', detail: 'No itinerary selected or invalid itinerary ID', life: 3000 });
        return;
      }

      if (!selectedLandmarks.value || selectedLandmarks.value.length === 0) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'No landmarks selected', life: 3000 });
        return;
      }

      let addedCount = 0; // Counter for successfully added landmarks

      try {
        for (let landmark of selectedLandmarks.value) {
          if (typeof landmark === 'string' && landmark.trim() !== '') {
            await ItineraryService.addLandmarkToItinerary(selectedItineraryId.value.itineraryId, landmark);
            addedCount++; // Increment the counter
          }
        }

        if (addedCount === selectedLandmarks.value.length) {
          toast.add({ severity: 'success', summary: 'Success', detail: 'All selected landmarks added to itinerary successfully', life: 3000 });
        } else {
          toast.add({ severity: 'warn', summary: 'Warning', detail: `${addedCount} out of ${selectedLandmarks.value.length} landmarks were added to the itinerary`, life: 5000 });
        }
      } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Error adding landmarks to itinerary: ' + error.message, life: 5000 });
      }
    };







const fetchLandmarks = async () => {
  try {
    const cityName = selectedCity.value?.value;
    if (cityName) {
      const response = await LandmarkService.returnLandmarks(cityName);
      if (response.status === 200) {
        landmarks.value = response.data.places;
      } else {
        // Handle non-200 responses if necessary
        toast.add({ severity: 'warn', summary: 'Warning', detail: 'Received a non-success response from the server', life: 5000 });
      }
    } else {
      toast.add({ severity: 'info', summary: 'Info', detail: 'City name is not defined', life: 3000 });
    }
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Error fetching landmarks: ' + error.message, life: 5000 });
  }
};


    const formatOpeningHours = (landmark) => {
      if (!landmark.currentOpeningHours || !landmark.currentOpeningHours.weekdayDescriptions) {
        return [];
      }
      return landmark.currentOpeningHours.weekdayDescriptions.map(hoursString => {
        return {
          day: getDayFromHoursString(hoursString),
          hours: getHoursFromHoursString(hoursString)
        };
      });
    };

    watch(selectedCity, (newValue, oldValue) => {
      if (newValue !== oldValue) {
        fetchLandmarks();
      }
    });

    const getDayFromHoursString = (hoursString) => {
      return hoursString.split(":")[0];
    };

    const getHoursFromHoursString = (hoursString) => {
      return hoursString.split(":").slice(1).join(":").trim();
    };

    const formatAccessibilityOptions = (options) => {
      if (!options) {
        return "Not available";
      }
      return Object.entries(options)
        .filter(([_, value]) => value)
        .map(([key, _]) => humanizeString(key))
        .join(", ");
    };

    const handleItinerarySelection = async () => {
      if (!selectedItineraryId.value) {
        return;
      }
      try {
        const response = await ItineraryService.findById(selectedItineraryId.value);
        const selectedItinerary = response.data;
        // Process the selected itinerary as needed
      } catch (error) {
        console.error("Error finding itinerary:", error);
      }
    };

    onMounted(fetchItineraries);

    const humanizeString = (str) => {
      return str.replace(/([A-Z])/g, " $1").trim();
    };

    return {
      landmarks,
      selectedCity,
      selectedLandmarks,
      selectedItineraryId,
      itineraries,
      addToItinerary,
      getDayFromHoursString,
      getHoursFromHoursString,
      formatAccessibilityOptions,
      humanizeString,
      formatOpeningHours,
      handleItinerarySelection
    };
  },
};
</script>

<style scoped>
.landmarks-container {
  padding: 1rem;
  max-width: 1200px;
  margin: auto;
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
}
#itinerary-select-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
}


.add-itinerary-btn {
  display: block;
  margin: 1rem auto;
  color: #ffffff;
  background-color: #10c469;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.accordion-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 1.1rem;
  padding: 0.5rem 1rem;
  border-radius: 4px;
}

.landmark-tab {
  margin-bottom: 1rem;
  border: 1px solid #444; 
  background-color: #262626; 
  border-radius: 8px;
}

.landmark-details {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 20px;
  padding: 1rem;
}

.landmark-info,
.map-container,
.image-container,
.hours-table,
#select-checkmark-area {
  background-color: #333333; 
  margin-top: 8px;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5); 
}

#select-checkmark-area {
  width: 6rem;
  margin-bottom: 0px !important;
  margin-left: 1rem;
}

.image-container img {
  width: 100%;
  height: auto;
  border-radius: 5px;
}

.hours-table table {
  width: 100%;
  border-collapse: collapse;
}

.hours-table th,
.hours-table td {
  border: 1px solid #555; 
  padding: 8px;
  text-align: left;
}

@media (max-width: 768px) {
  .landmark-details {
    grid-template-columns: 1fr;
  }

  .accordion-header {
    flex-direction: column;
    align-items: flex-start;
  }
}


.checkbox-container {
  display: flex;
  align-items: center;
  column-gap: 0.5rem;
}
</style>