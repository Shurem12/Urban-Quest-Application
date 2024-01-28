<template>
  <ToastService/>
  <Toast ref="toast" />
  <div v-if="message.text" class="message" 
         :class="{'message-success': message.type === 'success', 
                  'message-error': message.type === 'error'}">
      {{ message.text }}
    </div>
                <CreateItinerary/>
                <EditItinerary/>
    <ItineraryLandmarks/>
</template>

  
  
<script>
import { ref, reactive } from "vue";
import ItineraryService from "../services/ItineraryService";
import LandmarkService from "../services/LandmarkService";
import EditItinerary from '../components/EditItinerary.vue';
import CreateItinerary from '../components/CreateItinerary.vue';
import ItineraryLandmarks from '../components/ItineraryLandmarks.vue';
import ToastService from 'primevue/toastservice';
import Toast from 'primevue/toast';


export default {
  components: {
    EditItinerary,
    CreateItinerary,
    ItineraryLandmarks,
    ToastService,
    Toast
  },
  setup() {
    const itineraryName = ref("");
    const itineraryDate = ref(null);
    const selectedCity = ref(null);
    const startingPoint = ref(null);
    const landmarks = ref([]);
    const selectedLandmarks = ref([]);
    const createdItineraryId = ref(null);
    const isLoading = ref(false);
    const message = reactive({ text: "", type: "" });
    const errors = reactive({ name: "", date: "" });
    const itineraries = ref([]);
    const selectedItinerary = ref();
    const selectedPlaceId = ref(null);
    const dropdownLandmarks = ref([]);
    const toast = ref(null);

    async function getLandmarkDetails(placeId) {
      try {
        const response = await LandmarkService.fetchLandmarkDetails(placeId);
      } catch (error) {
        console.error("Error fetching landmark details:", error);
      }
    }

    const validateForm = () => {
      errors.name = itineraryName.value ? "" : "Itinerary name is required.";
      errors.date = itineraryDate.value ? "" : "Itinerary date is required.";
      return !errors.name && !errors.date;
    };

    const formatDate = (date) => {
      if (!date) return null;
      return date.toISOString().split("T")[0];
    };

    const fetchItineraryLandmarks = async () => {
      try {
        const response = await ItineraryService.returnAllItinerary();
        landmarks.value = response.data;
      } catch (error) {
        console.error("Error fetching landmarks:", error);
      }
    };
    
    fetchItineraryLandmarks();

    const fetchLandmarks = async () => {
      try {
        const cityName = selectedCity.value?.value;
        if (cityName) {
          const response = await LandmarkService.returnLandmarks(cityName);
          if (response.status === 200) {
            landmarks.value = response.data.places;
          }
        } else {
          console.error("City name is not defined");
        }
      } catch (error) {
        console.error("Error fetching landmarks:", error);
      }
    };

    fetchLandmarks();

    const fetchItineraries = async () => {
      try {
        const response = await ItineraryService.returnAllItinerary();
        itineraries.value = response.data;
      } catch (error) {
        console.error("Error fetching itineraries:", error);
      }
    };

    fetchItineraries();

    const createItinerary = async (itineraryData) => {
  if (!validateForm()) return;
  isLoading.value = true;
  console.log(itineraryData);
  try {
    const response = await ItineraryService.createItinerary(itineraryData);
    createdItineraryId.value = response.data;
    this.$toast.add({severity:'success', summary: 'Success', detail: 'Itinerary created successfully.', life: 3000});
    fetchItineraries();
  } catch (error) {
    this.$toast.add({severity:'error', summary: 'Error', detail: `Error creating itinerary: ${error.message}`, life: 5000});
  } finally {
    isLoading.value = false;
  }
};


const editItinerary = async (itinerary) => {
  if (!validateForm()) return;
  isLoading.value = true;

// let data = {
//     startDate: "2024-01-01"
// };

// let key = "startDate"
// data[key] = "2024-01-01"
  console.log(itinerary)

  try {
    // const editedItinerary = {
    //   ...itinerary,
    //   eventDate: formatDate(itineraryDate.value)
    // };

    // await ItineraryService.updateItinerary(itinerary.itineraryId, editedItinerary);
    await ItineraryService.updateItinerary(itinerary);

    toast.value.add({ severity: 'success', summary: 'Success', detail: 'Itinerary updated successfully.', life: 3000 });
  } catch (error) {
    console.error("An error occurred while updating the itinerary:", error);
    let detail = "Error updating itinerary. ";

    if (error.response) {
      console.error("Error data:", error.response.data);
      console.error("Error status:", error.response.status);
      console.error("Error headers:", error.response.headers);
      detail += `The server responded with a status code of ${error.response.status}.`;
    } else if (error.request) {
      console.error("No response received:", error.request);
      detail += "No response was received from the server.";
    } else {
      console.error("Error message:", error.message);
      detail += error.message;
    }

    toast.value.add({ severity: 'error', summary: 'Error', detail: detail, life: 5000 });
  } finally {
    isLoading.value = false;
  }
};



const deleteItinerary = async (itineraryId) => {
  if (confirm("Are you sure you want to delete this itinerary?")) {
    isLoading.value = true;
    try {
      await ItineraryService.deleteItinerary(itineraryId);
      toast.value.add({ severity: 'success', summary: 'Success', detail: 'Itinerary deleted successfully.', life: 3000 });
    } catch (error) {
      console.error("An error occurred while deleting the itinerary:", error);
      let detail = "Error deleting itinerary. ";

      if (error.response) {
        console.error("Error data:", error.response.data);
        console.error("Error status:", error.response.status);
        console.error("Error headers:", error.response.headers);
        detail += `The server responded with a status code of ${error.response.status}.`;
      } else if (error.request) {
        console.error("No response received:", error.request);
        detail += "No response was received from the server.";
      } else {
        console.error("Error message:", error.message);
        detail += error.message;
      }

      toast.value.add({ severity: 'error', summary: 'Error', detail: detail, life: 5000 });
    } finally {
      isLoading.value = false;
    }
  }
};



    const addLandmarksToItinerary = async () => {
      if (!createdItineraryId.value) return;
      isLoading.value = true;
      
      try {
        for (const landmark of selectedLandmarks.value) {
          await ItineraryService.addLandmarkToItinerary(
            createdItineraryId.value,
            landmark.id
          );
        }
        message.text = "Landmarks added to itinerary successfully.";
        message.type = "success";
      } catch (error) {
        message.text = "Error adding landmarks to itinerary: " + error.message;
        message.type = "error";
      } finally {
        isLoading.value = false;
      }
    };


    return {
      itineraryName,
      itineraryDate,
      selectedCity,
      startingPoint,
      landmarks,
      selectedLandmarks,
      createdItineraryId,
      createItinerary,
      editItinerary,
      deleteItinerary,
      addLandmarksToItinerary,
      isLoading,
      message,
      errors,
      itineraries,
      selectedItinerary,
      selectedPlaceId,
      dropdownLandmarks,
      Toast,
      toast
    };
  },
};
</script>


  
  
  

  <style scoped>
.itinerary-container h1 i,
.landmarks-section h2 i {
  margin-right: 0.5rem;
}
.error-message {
  color: red;
  font-size: 0.8em;
}

.message {
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
  text-align: center;
}

.message-success {
  color: #155724;
}

.message-error {
  color: #721c24;
}

.form-group {
  margin-bottom: 15px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.landmark-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.landmark-name {
  margin-left: 10px;
  cursor: pointer;
}
</style>