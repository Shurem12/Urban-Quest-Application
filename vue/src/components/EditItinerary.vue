<template>
  <Accordion>
    <AccordionTab class="edit-tab">
      <template #header>
        <div class="accordion-header">
          <span>Edit Itinerary</span>
        </div>
      </template>

      <div class="form-group">
        <Dropdown
          :options="itineraries"
          optionLabel="name"
          v-model="selectedItinerary"
          placeholder="Select Itinerary To Edit"
          class="custom-dropdown"
        />
      </div>

      <div class="form-group">
        <InputText
          placeholder="Rename Itinerary"
          v-model="itineraryNewName"
          class="custom-input"
        />
      </div>

      <div class="form-group">
        <Calendar
          placeholder="Select Date"
          v-model="itineraryDate"
          showTime
          hourFormat="24"
          :minDate="new Date()"
          dateFormat="yy-mm-dd"
          class="custom-calendar"
          touchUI
        />
      </div>
      
      <div class="form-group">
        <LandmarkQuickSelect/>
      </div>
      <div class="form-actions">
        
        <Button
          label="Save Changes"
          class="btn primary-btn"
          icon="pi pi-save"
          @click="saveItinerary"
        />
        <Button
          label="Cancel"
          class="btn secondary-btn"
          icon="pi pi-times"
          @click="cancelEdit"
        />
      </div>
      <div class="form-actions">
    <Button
      label="Delete"
      class="btn delete-btn"
      icon="pi pi-trash"
      @click="deleteItinerary(selectedItinerary.value.itineraryId)"
    />
  </div>
    </AccordionTab>
  </Accordion>
</template>
  
  <script>
import { ref, onMounted } from "vue";
import InputText from "primevue/inputtext";
import Calendar from "primevue/calendar";
import Button from "primevue/button";
import Dropdown from "primevue/dropdown";
import Accordion from "primevue/accordion";
import AccordionTab from "primevue/accordiontab";
import ItineraryService from "../services/ItineraryService";
import LandmarkQuickSelect from "./LandmarkQuickSelect.vue";

export default {
  components: {
    InputText,
    Calendar,
    Button,
    Dropdown,
    Accordion,
    AccordionTab,
    LandmarkQuickSelect
  },
  setup() {
    
    const selectedItinerary = ref(null);
    const itineraryDate = ref(null);
    const selectedLandmarks = ref([]);
    const selectedCity = ref(null);
    const selectedCategory = ref(null);
    const itineraries = ref([]);
    const itineraryNewName = ref();

    const fetchItineraries = async () => {
      try {
        const response = await ItineraryService.returnAllItinerary();
        itineraries.value = response.data;
      } catch (error) {
        console.error("Error fetching itineraries:", error);
      }
    };


    const deleteItinerary = async (itineraryId) => {
      if (!itineraryId) {
        console.error("No itinerary selected to delete.");
        // Additional user messaging can be added here
        return;
      }
      if (confirm("Are you sure you want to delete this itinerary?")) {
        try {
          await ItineraryService.deleteItinerary(itineraryId);
          console.log("Itinerary deleted successfully.");
          // Update UI or state as needed
        } catch (error) {
          console.error("Error deleting itinerary:", error);
          // Additional error handling
        }
      }
    };

    const saveItinerary = async () => {
  if (!selectedItinerary.value) {
    console.error("No itinerary selected to save.");
    // message.text = "No itinerary selected to save.";
    // message.type = "error";
    return;
  }

  const updatedItinerary = {
    itineraryId: selectedItinerary.value.itineraryId,
    name: selectedItinerary.value.name,
    eventDate: itineraryDate.value,
    startingPoint: selectedCity.value,
  };

  console.log("Sending updated itinerary data:", updatedItinerary); // Log data being sent

  try {
    const response = await ItineraryService.updateItinerary(updatedItinerary);
    console.log("Response received:", response); // Log the response

    if (response && response.data && response.data.success) {
      console.log("Itinerary updated successfully.");
      // message.text = "Itinerary updated successfully.";
      // message.type = "success";
    } else {
      console.error("Itinerary update not acknowledged by server.");
      // message.text = "Itinerary update failed.";
      // message.type = "error";
    }
  } catch (error) {
    console.error("Error updating itinerary:", error);
    // message.text = "Error updating itinerary. " + (error.message || "Unknown error occurred.");
    // message.type = "error";

    if (error.response) {
      console.error("Error data:", error.response.data);
      console.error("Error status:", error.response.status);
      console.error("Error headers:", error.response.headers);
    } else if (error.request) {
      console.error("No response received:", error.request);
    } else {
      console.error("Error message:", error.message);
    }
  }
};



    const cancelEdit = () => {
      // Logic to cancel editing
    };

    onMounted(fetchItineraries);

    return {
      selectedItinerary,
      itineraryDate,
      selectedLandmarks,
      selectedCity,
      selectedCategory,
      itineraries,
      itineraryNewName,
      saveItinerary,
      cancelEdit,
      deleteItinerary,
      LandmarkQuickSelect
    };
  },
};
</script>
  
  <style>
  .delete-btn {
  background-color: #f44336;
  color: white;
}
.custom-dropdown,
.custom-input,
.custom-calendar,
.custom-multiselect,
.custom-treeselect {
  margin-bottom: 15px;
  width: 100%;
}

.primary-btn {
  background-color: #4caf50; 
  color: white;
}

.secondary-btn {
  background-color: #f44336; 
  color: white;
}

.btn:hover {
  opacity: 0.8;
}

.accordion-header {
  font-size: 1.2em;
  font-weight: bold;
}

@media (max-width: 600px) {
  .form-group,
  .form-actions {
    width: 100%;
    padding: 0 10px;
  }
}
</style>