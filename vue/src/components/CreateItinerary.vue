<template>
    <div class="itinerary-container">
      <h1><i class="pi pi-fw pi-calendar-plus"></i> Itinerary Planner</h1>
  
      <Accordion>
        <AccordionTab class="edit-tab">
          <template #header>
            <div class="accordion-header">
              <span>Create Itinerary</span>
            </div>
          </template>
  
          <div class="form-group">
            <InputText placeholder="Itinerary Name" v-model="itineraryName" />
            <p v-if="errors.name" class="error-message">{{ errors.name }}</p>
          </div>
  
          <div class="form-group">
            <Calendar placeholder="Select Date" v-model="itineraryDate" 
                      showTime hourFormat="24" :minDate="new Date()" 
                      dateFormat="yy-mm-dd" />
            <p v-if="errors.date" class="error-message">{{ errors.date }}</p>
          </div>
  
          <div class="form-group">
            <LandmarkQuickSelect v-if="selectedCity" :city="selectedCity" 
                                 v-model="startingPoint" />
          </div>
  
          <div class="form-actions">
            <Button label="Create Itinerary" class="btn" icon="pi pi-check" 
                    @click="createItinerary" :disabled="isLoading" />
          </div>
  
          <div v-if="message.text" class="message" 
               :class="{'message-success': message.type === 'success', 
                        'message-error': message.type === 'error'}">
            {{ message.text }}
          </div>
        </AccordionTab>
      </Accordion>
    </div>
  </template>
  
  <script>
  import { ref, reactive } from "vue";
  import InputText from "primevue/inputtext";
  import Calendar from "primevue/calendar";
  import Button from "primevue/button";
  import Accordion from "primevue/accordion";
  import AccordionTab from "primevue/accordiontab";
  import LandmarkQuickSelect from "../components/LandmarkQuickSelect.vue";
  import ItineraryService from "../services/ItineraryService";
  
  export default {
    components: {
      InputText,
      Calendar,
      Button,
      Accordion,
      AccordionTab,
      LandmarkQuickSelect
    },
    setup() {
      const itineraryName = ref("");
      const itineraryDate = ref(null);
      const selectedCity = ref(null);
      const startingPoint = ref(null);
      const isLoading = ref(false);
      const message = reactive({ text: "", type: "" });
      const errors = reactive({ name: "", date: "" });
      const validateForm = () => {
        errors.name = itineraryName.value ? "" : "Itinerary name is required.";
        errors.date = itineraryDate.value ? "" : "Itinerary date is required.";
        return !errors.name && !errors.date;
      };
      const createItinerary = async () => {
  if (!validateForm()) return;
  isLoading.value = true;
  try {
    const newItinerary = {
      name: itineraryName.value,
      eventDate: itineraryDate.value,
      startingPoint: startingPoint.value,
    };
    const response = await ItineraryService.createItinerary(newItinerary);
    if (response && response.data) {
      const createdItineraryId = response.data;
      console.log(`Itinerary created successfully with ID: ${createdItineraryId}`);
    }
    message.text = "Itinerary created successfully.";
    message.type = "success";
    resetForm();
  } catch (error) {
    console.error("Error creating itinerary:", error);
    message.text = "Error creating itinerary. ";
    if (error.response) {
      console.error("Error data:", error.response.data);
      console.error("Error status:", error.response.status);
      console.error("Error headers:", error.response.headers);
      message.text += `The server responded with a status code of ${error.response.status}.`;
    } else if (error.request) {
      console.error("No response received:", error.request);
      message.text += "No response was received from the server.";
    } else {
      console.error("Error message:", error.message);
      message.text += error.message;
    }

    message.type = "error";
  } finally {
    isLoading.value = false;
  }
};


const resetForm = () => {
  itineraryName.value = '';
  itineraryDate.value = null;
  startingPoint.value = null;
};
  
      return {
        itineraryName,
        itineraryDate,
        selectedCity,
        startingPoint,
        isLoading,
        message,
        errors,
        createItinerary
      };
    },
  };
  </script>
  
  <style scoped>
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
    background-color: #d4edda;
    color: #155724;
  }
  .message-error {
    background-color: #f8d7da;
    color: #721c24;
  }
  </style>
  