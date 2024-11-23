package org.example.airport.service;

import org.example.airport.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripsService {
    Trip addTrip(Trip trip);
    Trip updateTrip(Trip trip);
    Trip deleteTrip(int id);
    Page<Trip> searchTrips(String query, Pageable pageable);
}
