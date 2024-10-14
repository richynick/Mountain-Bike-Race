package com.richard.bikeRaceService.repository;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {
//    findByRaceIdOrderByFinishTimeAsc

    //    List<RaceResult> findByFinishTimeGreaterThan(Double endTime);
    Optional<RaceResult> findByRiderAndRace(Rider rider, Race race);
    List<RaceResult> findByRaceIdOrderByFinishTime(Long raceId);
    List<RaceResult> findByRaceAndStatus(Race raceName, String status);
}
