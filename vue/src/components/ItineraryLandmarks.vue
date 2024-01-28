<template>
  <div class="landmarks-container">
    <Dropdown
      :options="itineraries"
      optionLabel="name"
      v-model="selectedItineraryId"
      placeholder="Select an Itinerary"
    ></Dropdown>
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
import { ref } from "vue";
import Accordion from "primevue/accordion";
import AccordionTab from "primevue/accordiontab";
import Dropdown from "primevue/dropdown";
import ImageMapSwap from "../components/ImageMapSwap.vue";
import DataTable from "primevue/datatable";
import Column from "primevue/column";

export default {
  components: {
    ImageMapSwap,
    Accordion,
    AccordionTab,
    Dropdown,
    DataTable,
    Column,
  },
  setup() {
    const selectedCity = ref({
      value: "Dubai", // Replace with appropriate value as needed
    });

    const formatOpeningHours = (landmark) => {
      // Basic implementation - adjust according to your requirements
      return landmark.currentOpeningHours.weekdayDescriptions.map(
        (desc, index) => {
          return {
            day: desc.split(":")[0],
            hours: desc.split(":").slice(1).join(":").trim(),
          };
        }
      );
    };

    const landmarks = ref([
      {
        types: [
          "landmark",
          "tourist_attraction",
          "point_of_interest",
          "establishment",
        ],
        formattedAddress:
          "1 Sheikh Mohammed bin Rashid Blvd - Downtown Dubai - Dubai - United Arab Emirates",
        location: {
          latitude: 25.197197,
          longitude: 55.274376399999994,
        },
        rating: 4.7,
        displayName: {
          text: "Burj Khalifa",
          languageCode: "en",
        },
        currentOpeningHours: {
          openNow: true,
          periods: [
            {
              open: {
                day: 4,
                hour: 0,
                minute: 0,
                truncated: true,
                date: {
                  year: 2024,
                  month: 1,
                  day: 25,
                },
              },
              close: {
                day: 3,
                hour: 23,
                minute: 59,
                truncated: true,
                date: {
                  year: 2024,
                  month: 1,
                  day: 31,
                },
              },
            },
          ],
          weekdayDescriptions: [
            "Monday: Open 24 hours",
            "Tuesday: Open 24 hours",
            "Wednesday: Open 24 hours",
            "Thursday: Open 24 hours",
            "Friday: Open 24 hours",
            "Saturday: Open 24 hours",
            "Sunday: Open 24 hours",
          ],
        },
        photos: [
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFgMqOEVm9XBluCpp2yczyqHjANfIT_sKnQ6cmkb60VzF5em6I-GJkgTHK7LV1rJrNQPhUHjNNqmaJ7XVM2LE4XCmzXmzebiDxcKHOfbJIvmCPr-5ulbipr1h7sdPYPi3q0zBgtpo1OkotcXngud-vi9nkapyAqK3mWF",
            widthPx: 9000,
            heightPx: 12000,
            authorAttributions: [
              {
                displayName: "SHIBILI ANWAR HUDAWI",
                uri: "//maps.google.com/maps/contrib/116503448091371588275",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjWssg4VF__LPTgeWbcJbLpEm2A-mEykch3fFeCSrzyqjLY=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFhjPdOsfumjm3mZQKAmGDrRQXmtf_HO3DCnlQUpKGyTbygJq4ZEMuyGwrO45ysH968hevrVd0JuNXTpQcrw9yFbOpRtbibIQh5SXYY1U09peWUbgmij-1jHI2Vr0xTT_eAI5g_QHmmyJfMJ_ra7RLZDZ9HFF9d03_jc",
            widthPx: 1024,
            heightPx: 1024,
            authorAttributions: [
              {
                displayName: "Rami Maki",
                uri: "//maps.google.com/maps/contrib/111767122575451188220",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjX-gnITrdyK7l2Te32lV3AI4dPjVoNRgbDPG7CV7wCB26t-=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFiMJhnT3QsQTTwYywDV29N72rrJmGpXSVMPu9NrhIAil-wTTFzLuwpmyWueXcOTLgd8xB14H-tCct8xAmc9nC-rYiUJB8NPzTZ-h_sGXcdG4xiGa7jtvRCBn5xBRKYZCFw0VhidxqRNjg5s_MR5yH_21jL7aAcSs4Po",
            widthPx: 1200,
            heightPx: 800,
            authorAttributions: [
              {
                displayName: "عوض جبران الخلاوي الرسلاني",
                uri: "//maps.google.com/maps/contrib/109276397711912537324",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjUf-eErs6gFhZSXnjQSWmmVXKel2Lfac5aYgZIthox7tGs=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFhn-eJLQq7LGe-bZWeUX7K9Gy7nexwfx-TFq0Fb6ViPfYj_L0gJPJZj7ZxbtLuq6nRi9FVgfqSY1gkybN2hL1mFMLOgjoGiNY_7BQphWgW_pMm9R4mKG4jOJAWlrAx6s7CHLjhn-hDHIrytocW5Wmy34nP9cD5YF1a_",
            widthPx: 12000,
            heightPx: 9000,
            authorAttributions: [
              {
                displayName: "Rezaul Afzal",
                uri: "//maps.google.com/maps/contrib/104041742298427945686",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjVVsPvKCk8NJkB8elsXwdyxMM3CehzmQebbo0OvRkcZJHI=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFgy3Qrm_tfWF93UtooDKPt1tcJj3jPtNso2SUnVu6h_As7fmzjLIKz7VCUz0bIHQA1hEbC8529zJk4vUnp8PuVZ3CPbrdanIWOfE2nszE-Vhxtb6JHqoSRoRuk5xzOPb6C2DEoZVzNDUWWlZdNXTu52EOa-t534tcNO",
            widthPx: 3024,
            heightPx: 4032,
            authorAttributions: [
              {
                displayName: "Kevin W.",
                uri: "//maps.google.com/maps/contrib/112636327942629873757",
                photoUri:
                  "//lh3.googleusercontent.com/a/ACg8ocJYFVNasCCSqYOFvh1qvAbw7Nr2FLnlyj0EKk1gw5ue=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFhWTa_obwY8PU2JzOgerd4YDLZU-LQAkNvH9V_x6v7rH1RJboH1p7pMlifjnOEC64hjHcxFOTeKXTH8AS8R8aJCsYeWHHd6BM_j4Sqwbr4BCJzjTBFbYx3ceC8ZPufzA3YQkiATpaZFYPnrc-7es6CFD-ZwPlDhueAg",
            widthPx: 9000,
            heightPx: 12000,
            authorAttributions: [
              {
                displayName: "marcos ribeiro",
                uri: "//maps.google.com/maps/contrib/113379368257508244064",
                photoUri:
                  "//lh3.googleusercontent.com/a/ACg8ocJVo4xnTRu5gEGyAemXxXoqnLiD2kitEujn9Q7-D2Kx=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFj-_VZmPlC0eQV4QAONPMOS9wM0H33vKVW8cw1pCJmrtnqmcJXpMm8ja2yuKHdmuqMpRcNHanWwz8xY56jLXVEDuVKBYnXwXM_QNjcD0eu9Q8IIaf0Win1QzNmv2rH4UjR5beakTMKuQKu0VPayddfK_LF0TNPJlRZd",
            widthPx: 4624,
            heightPx: 3472,
            authorAttributions: [
              {
                displayName: "Engin Deniz",
                uri: "//maps.google.com/maps/contrib/107459145410943059098",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjW5pvEpFmADQn09EA_GuDvtpp_iMfA8hdmAwJAyPMga5mU=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFitgt7tA7SLxhXFxVWeJFgAUkpl5N47psWUX4AviNYQubJeG9tI9DVHwB2HkI4V-idEIWsibQvWTvgUlglTKBRYVekmLHHjXMI5PMBqZKDYSpLTY1rmlSOEelOpSZEhizHqR1hmCbE6xZnqBX3_oSc2uiuPmlzgegYT",
            widthPx: 3024,
            heightPx: 4032,
            authorAttributions: [
              {
                displayName: "ELSA MARY",
                uri: "//maps.google.com/maps/contrib/113372628500646001054",
                photoUri:
                  "//lh3.googleusercontent.com/a/ACg8ocKQwdRmg93njKX32_4n3D0m9nc3fSR0qkMGcMPFL_nvNA=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFgb2NgsfvBaYHCP9qCL3ueBegdG0PPcSyjBlKeo-0Q0jQqyCPB6SU_WW_78vHZfCm2f3BY6355_X_Z-kvQZv7finfZ1T6WKGkdebj8T5D051E0AXuX3g32ePOinek7vCiQht51TbjmJ1MU7iJ5jM-lUBkaPlQxKREmX",
            widthPx: 3024,
            heightPx: 4032,
            authorAttributions: [
              {
                displayName: "Daniel John",
                uri: "//maps.google.com/maps/contrib/118358162462860729199",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjViLNYZhdeT_Po-oA4R_Q0nLajD6UQmqTKId-uvSx0xI10=s100-p-k-no-mo",
              },
            ],
          },
          {
            name: "places/ChIJS-JnijRDXz4R4rfO4QLlRf8/photos/AWU5eFgx2rfo375jOudEcBzv_6A2ND8PT0g81SrXSaBPlAKt_j41WiZS2E4e8bge_T9aY8MbBUXFj0QWjjJWXwEgMCo5byMcsHXVrAZ5H9l5rRiAqDtHxeb9LrNFj4hSDnMrsdm3y2e4t1UR2oJJ-Z7INsd_xWYUvjfPBOxp",
            widthPx: 2268,
            heightPx: 4032,
            authorAttributions: [
              {
                displayName: "The Batman",
                uri: "//maps.google.com/maps/contrib/108981272317000602763",
                photoUri:
                  "//lh3.googleusercontent.com/a-/ALV-UjU-lis4xv6EfSJOdCQrnPNMeZBNvy3ha5CjToz5QeJc7Jy5=s100-p-k-no-mo",
              },
            ],
          },
        ],
        accessibilityOptions: {
          wheelchairAccessibleParking: true,
          wheelchairAccessibleEntrance: true,
          wheelchairAccessibleRestroom: true,
          wheelchairAccessibleSeating: true,
        },
      },
      
    ]);

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
      formatAccessibilityOptions,
      landmarks,
      selectedCity,
      formatOpeningHours,
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
  