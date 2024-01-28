<template>
  <!-- Country Dropdown -->
  <Dropdown filter v-model="selectedCountry" :options="countries" optionLabel="label" placeholder="Select a Country" class="w-full md:w-14rem" @change="loadStatesForCountry" />

  <!-- State/Province Dropdown - Shown if states are available -->
  <Dropdown filter v-model="selectedState" :options="states" optionLabel="label" placeholder="Select a State/Province" class="w-full md:w-14rem" v-if="states.length > 0" @change="loadCitiesForState" />

  <!-- City Dropdown - Shown when cities are available -->
  <Dropdown filter v-model="selectedCity" :options="cities" optionLabel="label" placeholder="Select a City" class="w-full md:w-14rem" v-if="cities.length > 0" />
</template>


  
<script>
import { ref, watch } from 'vue';
import Dropdown from 'primevue/dropdown';
import { Country, State, City } from 'country-state-city';

export default {
  components: {
    Dropdown
  },
  setup(props, { emit }) {
    const selectedCountry = ref(null);
    const selectedState = ref(null);
    const selectedCity = ref(null);
    const countries = ref([]);
    const states = ref([]);
    const cities = ref([]);

    // Load countries
    countries.value = Country.getAllCountries().map(country => ({
      label: country.name,
      code: country.isoCode
    }));

    // Load states for the selected country
    function loadStatesForCountry() {
      if (selectedCountry.value) {
        states.value = State.getStatesOfCountry(selectedCountry.value.code).map(state => ({
          label: state.name,
          code: state.isoCode
        }));
        selectedState.value = null; // Reset selected state
        cities.value = []; // Reset cities
      }
    }

    // Load cities for the selected state
    function loadCitiesForState() {
      if (selectedState.value) {
        cities.value = City.getCitiesOfState(selectedCountry.value.code, selectedState.value.code).map(city => ({
          label: city.name,
          value: city.name
        }));
      }
    }

    watch(selectedCity, (newValue) => {
      emit('update:modelValue', newValue);
    });

    return {
      selectedCountry,
      selectedState,
      selectedCity,
      countries,
      states,
      cities,
      loadStatesForCountry,
      loadCitiesForState
    };
  }
};
</script>


  