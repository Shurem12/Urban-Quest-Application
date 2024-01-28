package com.techelevator.dao;

import com.techelevator.model.Landmark;
import com.techelevator.model.searchlandmark.Landmarks;

import java.util.List;

public interface LandmarksDao {
    Landmarks create(String placeId);

    void delete(String placeId);

    List<Landmark> listByItineraryId(int itineraryId);
}
