<template>
    <div>
      <h3>Recently Viewed Landmarks</h3>
      <Listbox 
        v-if="recents && recents.length > 0" 
        v-model="selectedLandmark" 
        :options="recents" 
        optionLabel="name"
        @change="onSelectLandmark" />
    </div>
  </template>
  
  <script>
  import Listbox  from 'primevue/listbox';
  
  export default {
    components: {
      Listbox
    },
    props: {
      recents: Array
    },
    data() {
      return {
        selectedLandmark: null
      };
    },
    methods: {
      onSelectLandmark() {
        this.$emit('landmarkSelected', this.selectedLandmark);
      }
    }
  };
  </script>
  