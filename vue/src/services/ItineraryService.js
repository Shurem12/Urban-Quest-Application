import axios from "axios";
const BASE_URL = "/itinerary/";

export default {
  returnAllItinerary() {
    return axios.get(BASE_URL + "find_all");
  },
  //Working
  createItinerary(itinerary) {
    return axios.post(BASE_URL + "create", itinerary);
  },
  updateItinerary(itinerary) {
    return axios.put(BASE_URL + "update", itinerary); // Change to PUT for update
  },
  deleteItinerary(itineraryId) {
    return axios.post(BASE_URL + `delete?itinerary_id=${itineraryId}`)// Change to DELETE for delete
  },
  addLandmarkToItinerary(itineraryId, placeId) {
    return axios.post(BASE_URL + `add_landmark?itinerary_id=${itineraryId}&place_id=${placeId}`) 
  },
  findById(itineraryId) {
    return axios.get(BASE_URL + `find?itinerary_id=${itineraryId}`)
  }
}
