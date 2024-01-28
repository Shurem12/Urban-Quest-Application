import axios from 'axios';

const BASE_URL = '/landmarks/';

export default {
    returnLandmarks(city) {
        return axios.get(`${BASE_URL}search?city=${city}`);
    },

    fetchLandmarkDetails(placeId) {
        return axios.get(`${BASE_URL}find_by_place_id/${placeId}`);
    },

    addLandmark(landmarkData) {
        return axios.post(`${BASE_URL}add`, landmarkData);
    },

    updateLandmark(placeId, updatedData) {
        return axios.put(`${BASE_URL}update/${placeId}`, updatedData);
    },

    deleteLandmark(placeId) {
        return axios.delete(`${BASE_URL}delete/${placeId}`);
    }
}
