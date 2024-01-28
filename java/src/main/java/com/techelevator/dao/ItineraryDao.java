package com.techelevator.dao;

import com.techelevator.model.Itinerary;
import com.techelevator.model.Landmark;

import java.util.List;

public interface ItineraryDao {
    int create(Itinerary itinerary);

    Itinerary findById(int itineraryId);

    List<Itinerary> findAll();

    Itinerary update(Itinerary itinerary);

    void delete(int itineraryId);

    int addLandmarkToItinerary(int itineraryId, String placeId);

    void removeLandmarkFromItinerary(int itineraryId, String placeId);
    void removeLandmarkFromAllItinerary(String placeId);

}
