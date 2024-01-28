<template>
    <div class="landmarks-container">
        <Dropdown :options="itineraries" optionLabel="name" v-model="selectedItineraryId" 
              placeholder="Select an Itinerary"></Dropdown>
      <h1>Itinerary Landmarks</h1>
  
      <Accordion>
        <AccordionTab
          v-for="landmark in landmarks"
          :key="landmark.id"
          class="landmark-tab"
        >
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
    </div>
  </template>
  
  
  <script>
  import { ref, onMounted, watch } from "vue";
  import Accordion from "primevue/accordion";
  import AccordionTab from "primevue/accordiontab";
  import Dropdown from "primevue/dropdown";
  import LandmarkService from "../services/LandmarkService";
  import ImageMapSwap from "../components/ImageMapSwap.vue";
  import ItineraryService from "../services/ItineraryService";
  import DataTable from 'primevue/datatable';
  import Column from 'primevue/column';
  
  export default {
    components: {
      ImageMapSwap,
      Accordion,
      AccordionTab,
      Dropdown,
      DataTable,
      Column
    },
    setup() {
      const landmarks = ref([]);
      const selectedItineraryId = ref(null);
      const itineraries = ref([]);
  
      const fetchItineraries = async () => {
        try {
          const response = await ItineraryService.returnAllItinerary();
          itineraries.value = response.data;
        } catch (error) {
          console.error("Error fetching itineraries:", error);
        }
      };
  
      const fetchLandmarkDetails = async (placeId) => {
        try {
          const response = await LandmarkService.fetchLandmarkDetails(placeId);
          if (response.status === 200) {
            landmarks.value.push(response.data);
          }
        } catch (error) {
          console.error("Error fetching landmark details:", error);
        }
      };
  
      const fetchLandmarksForItinerary = async () => {
        landmarks.value = []; // Reset landmarks
        if (selectedItineraryId.value) {
          // Assuming the itinerary object has a 'placeIds' array
          const itinerary = itineraries.value.find(it => it.id === selectedItineraryId.value);
          if (itinerary && itinerary.placeIds) {
            for (let id of itinerary.placeIds) {
              await fetchLandmarkDetails(id);
            }
          }
        }
      };
  
      watch(selectedItineraryId, () => {
        fetchLandmarksForItinerary();
      });
  
      onMounted(fetchItineraries);
  
      const formatAccessibilityOptions = (options) => {
        if (!options) {
          return "Not available";
        }
        return Object.entries(options)
          .filter(([_, value]) => value)
          .map(([key, _]) => key.replace(/([A-Z])/g, " $1").trim())
          .join(", ");
      };
  
      return {
        landmarks,
        selectedItineraryId,
        itineraries,
        formatAccessibilityOptions
      };
    },
  };
  </script>
  
  
  

  
  <style>
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
  