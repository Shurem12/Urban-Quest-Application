package com.techelevator.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.techelevator.dao.LandmarksDao;
import com.techelevator.dao.ItineraryDao;
import com.techelevator.model.Landmark;
import com.techelevator.services.GoogleMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/landmarks")
public class LandmarksController {

    private final String API_KEY = Dotenv.configure().load().get("GOOGLE_MAPS_API_KEY");
    private final GoogleMaps googleMaps = new GoogleMaps(API_KEY);

    @Autowired
    private LandmarksDao landmarksDao;
    @Autowired
    private ItineraryDao itineraryDao;

    /*
     * example query
     * localhost:9000/landmarks/create?place_id=1234567890
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void create(@RequestParam(value = "place_id") String placeId) {
        landmarksDao.create(placeId);
    }

    /*
     * example query
     * localhost:9000/landmarks/delete?place_id=1234567890
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam(value = "place_id") String placeId) {
        itineraryDao.removeLandmarkFromAllItinerary(placeId);
        landmarksDao.delete(placeId);
    }

    /*
     * localhost:9000/landmarks/list_by_itinerary_id/1234567890
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/list_by_itinerary_id/{itineraryId}", method = RequestMethod.GET)
    public List<Landmark> listByItineraryId(@PathVariable int itineraryId) {
        return landmarksDao.listByItineraryId(itineraryId);
    }

    /*
     * localhost:9000/landmarks/search?city=dubai
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "city") String city) {
        String query = city + "things to do";
        return googleMaps.search(query);
    }
    /*
     * Fetch landmark details by placeId.
     * GET localhost:9000/landmarks/find_by_place_id/{placeId}
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/find_by_place_id/{placeId}", method = RequestMethod.GET)
    public String findByPlaceId(@PathVariable String placeId) {
        return googleMaps.findByPlaceId(placeId);
    }


    /*
     * localhost:9000/landmarks/search_photos?query=new york
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/search_photos", method = RequestMethod.GET)
    public String searchPhotos(@RequestParam(value = "query") String query) {
        return googleMaps.searchPhotos(query);
    }
}