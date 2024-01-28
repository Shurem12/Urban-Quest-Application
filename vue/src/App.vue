<template>
  <div id="capstone-app">
    <Menubar :model="items" />

    <router-view />
  </div>
</template>

<script>
import Menubar from "primevue/menubar";

export default {
  components: {
    Menubar,
  },
  data() {
    return {
      items: [
        {
          label: "Search",
          command: () => {
            this.$router.push({ name: "search" });
          },
        },
        {
          label: "Itinerary",
          command: () => {
            this.$router.push({ name: "itinerary" });
          },
          visible: this.$store.state.token !== "",
        },
        {
          label: "Logout",
          command: () => {
            this.$router.push({ name: "logout" });
          },
          visible: this.$store.state.token !== "",
        },
        {
          label: "Login",
          command: () => {
            this.$router.push({ name: "login" });
          },
          visible: this.$store.state.token === "",
        }
      ],
    };
  },
};
</script>

<style>
/* Additional styling if needed */
</style>

