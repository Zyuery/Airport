package org.example.airport.service.servicelmpl;

import org.example.airport.domain.Trip;
import org.example.airport.repository.TripDao;
import org.example.airport.service.TripsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TripServicelmpl implements TripsService {
    private final TripDao tripDao;

    public TripServicelmpl(TripDao tripDao) {
        this.tripDao = tripDao;
    }
    // 增：添加行程
    @Override
    public Trip addTrip(Trip trip) {
        return tripDao.save(trip);
    }
    // 改：更新行程
    @Override
    public Trip updateTrip(Trip trip) {
        Trip existingTrip = tripDao.findById(trip.getId());
        if (existingTrip!= null) {
            existingTrip.setTripNumber(trip.getTripNumber());
            existingTrip.setStartTime(trip.getStartTime());
            existingTrip.setEndTime(trip.getEndTime());
            existingTrip.setStartLocation(trip.getStartLocation());
            existingTrip.setEndLocation(trip.getEndLocation());
            existingTrip.setFlightNumber(trip.getFlightNumber());
            existingTrip.setAirline(trip.getAirline());
            existingTrip.setCabinClass(trip.getCabinClass());
            existingTrip.setUser(trip.getUser());
            return tripDao.save(existingTrip);
        }
        return null;
    }
    // 删：删除行程
    @Override
    public Trip deleteTrip(int id) {
        Trip tripToDelete = tripDao.findById(id);
        if (tripToDelete!= null) {
            tripDao.delete(tripToDelete);
            return tripToDelete;
        }
        return null;
    }
    // 查：模糊查询行程
    @Override
    public Page<Trip> searchTrips(String query, Pageable pageable) {
        return tripDao.findByFuzzyQuery(query, pageable);
    }
}
