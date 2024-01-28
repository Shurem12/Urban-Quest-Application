package com.techelevator.model.searchlandmark;

import java.time.LocalTime;

public class Landmarks {

    private String placeId;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String landmarkDescription;
    private int tourPriceRange;
    private String landmarkCategory;
    private int starRating;
    private double latitude;
    private double longitude;
    private String locationAddress;
    private String landmarkName;

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public String getLandmarkDescription() {
        return landmarkDescription;
    }

    public void setLandmarkDescription(String landmarkDescription) {
        this.landmarkDescription = landmarkDescription;
    }

    public int getTourPriceRange() {
        return tourPriceRange;
    }

    public void setTourPriceRange(int tourPriceRange) {
        this.tourPriceRange = tourPriceRange;
    }

    public String getLandmarkCategory() {
        return landmarkCategory;
    }

    public void setLandmarkCategory(String landmarkCategory) {
        this.landmarkCategory = landmarkCategory;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }



}
