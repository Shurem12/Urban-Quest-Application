package com.techelevator.controller;

import com.techelevator.dao.ItineraryDao;
import com.techelevator.dao.LandmarksDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(path = "/itinerary")
public class ItineraryController implements BaseController {

    @Autowired
    private LandmarksDao landmarksDao;
    @Autowired
    private ItineraryDao itineraryDao;
    @Autowired
    private UserDao userDao;

    @Override
    public String whoAmI() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public int currentUserId() {
        String username = whoAmI();
        return userDao.getUserByUsername(username).getId();
    }

    /*
     * localhost:9000/itinerary/create
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public int create(@RequestBody Itinerary itinerary) {
        itineraryDao.create(itinerary);
        userDao.addItinerary(currentUserId(), itinerary.getItineraryId());
        return itinerary.getItineraryId();
    }

    /*
     * localhost:9000/itinerary/find?itinerary_id=2001
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public Itinerary findById(@RequestParam(value = "itinerary_id") int itineraryId) {
        return itineraryDao.findById(itineraryId);
    }

    /*
     * localhost:9000/itinerary/find_all
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/find_all", method = RequestMethod.GET)
    public List<Itinerary> findAll() {
        return itineraryDao.findAll();
    }

    /*
     * localhost:9000/itinerary/update
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/update", method = RequestMethod.PUT)  // Change to PUT
    public ResponseEntity<?> update(@RequestBody Itinerary itinerary) {
        try {
            Itinerary updatedItinerary = itineraryDao.update(itinerary);
            return ResponseEntity.ok(updatedItinerary);  // Return the updated itinerary
        } catch (DaoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());  // Return a descriptive error message
        }
    }

    /*
     * localhost:9000/itinerary/delete?itinerary_id=2003
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam(value = "itinerary_id") int itineraryId) {
        userDao.deleteItinerary(itineraryId);
        itineraryDao.delete(itineraryId);
    }

    /*
     * localhost:9000/itinerary/add_landmark?itinerary_id=2003&place_id=some_string
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/add_landmark", method = RequestMethod.POST)
    public int addLandmarkToItinerary(@RequestParam(value = "itinerary_id") int itineraryId,
                           @RequestParam(value = "place_id") String placeId) {
        landmarksDao.create(placeId);
        return itineraryDao.addLandmarkToItinerary(itineraryId, placeId);
    }
    /*
     * localhost:9000/itinerary/remove_landmark?itinerary_id=2003&place_id=some_string
     * */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/remove_landmark", method = RequestMethod.POST)
    public void removeLandmarkFromItinerary(@RequestParam(value = "itinerary_id") int itineraryId,
                                           @RequestParam(value = "place_id") String placeId) {
        itineraryDao.removeLandmarkFromItinerary(itineraryId, placeId);
    }
}
