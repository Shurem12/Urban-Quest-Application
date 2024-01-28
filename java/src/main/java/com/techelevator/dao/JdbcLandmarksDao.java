package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Landmark;
import com.techelevator.model.searchlandmark.Landmarks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcLandmarksDao implements LandmarksDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Landmarks create(String placeId) {
        Landmarks newLandmark = new Landmarks();
        String sql = "insert into landmarks (place_id) VALUES (?);";

        try {
            jdbcTemplate.update(sql, placeId);
        } catch (Exception e) {
            throw new DaoException("Landmark not created");
        }

        return newLandmark;
    }

    @Override
    public void delete(String placeId) {
        String sql = "delete from landmarks where place_id = ?;";

        try {
            jdbcTemplate.update(sql, placeId);
        } catch (Exception e) {
            String message = String.format("Landmark not deleted: place_id(%s)", placeId);
            throw new DaoException(message);
        }
    }

    @Override
    public List<Landmark> listByItineraryId(int itineraryId) {
        List<Landmark> landmarks = new ArrayList<>();
        String sql = "select * from itinerary_landmarks where itinerary_id = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, itineraryId);
            while (result.next()) {
                landmarks.add(mapRowToLandmark(result));
            }
        } catch (Exception e) {
            throw new DaoException("Landmark not found");
        }
        return landmarks;
    }

    private Landmark mapRowToLandmark(SqlRowSet result) {
        Landmark landmark = new Landmark();
        landmark.setPlaceId(result.getString("place_id"));
        return landmark;
    }
}