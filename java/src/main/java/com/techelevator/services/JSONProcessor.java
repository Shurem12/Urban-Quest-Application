package com.techelevator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.itinerarylandmark.PlaceResult;
import com.techelevator.model.searchlandmark.LandmarkResult;
import com.techelevator.model.searchlandmark.SearchResultList;

import java.util.List;

public class JSONProcessor {
    public static SearchResultList JSONParserMultiple(String input) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SearchResultList landmarks = mapper.readValue(input, SearchResultList.class);
            return landmarks;
        } catch (Exception ex) {
            System.out.println("Error parsing API request");
        }
        return null;
    }
    public static LandmarkResult JSONParserSingle(String input) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LandmarkResult landmarks = mapper.readValue(input, LandmarkResult.class);
            return landmarks;
        } catch (Exception ex) {
            System.out.println("Error parsing API request");
        }
        return null;
    }
}