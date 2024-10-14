package com.richard.bikeRaceService.repository;

import com.richard.bikeRaceService.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByName(String name);

    @Query("SELECT r FROM Rider r WHERE r.id NOT IN " +
            "(SELECT rr.rider.id FROM RaceResult rr WHERE rr.race.name = :raceName)")
    List<Rider> findRidersNotInRace(@Param("raceName") String raceName);
}
