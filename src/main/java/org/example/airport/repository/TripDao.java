package org.example.airport.repository;

import org.example.airport.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TripDao extends JpaRepository<Trip, Integer> {
    Trip findByTripNumber(String tripNumber);
    Trip findById(int id);
    // 模糊查询行程, 分页
    @Query("SELECT t FROM Trip t WHERE" +
            " t.startLocation LIKE %:query% OR " +
            "t.endLocation LIKE %:query% OR " +
            "t.tripNumber LIKE %:query% OR "+
            "t.airline LIKE %:query% OR " +
            "t.flightNumber LIKE %:query%")
    Page<Trip> findByFuzzyQuery(@Param("query") String query, Pageable pageable);
}
