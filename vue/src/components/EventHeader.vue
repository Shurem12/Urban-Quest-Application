<template>
  <!-- Date and Time Dropdown -->
  <Calendar
    id="calendar-12h"
    v-model="selectedDateTime"
    showTime
    hourFormat="24"
    :minDate="currentDate"
    dateFormat="yyyy-mm-dd"
  />
</template>

<script>
import { ref, watch } from "vue";
import Calendar from "primevue/calendar";

export default {
  components: {
    Calendar,
  },
  setup(props, { emit }) {
    const selectedDateTime = ref();

    watch(selectedDateTime, (newValue) => {
      emit("update:modelValue", newValue);
    });

    return {
      selectedDateTime,
    };
  },
};
</script>
