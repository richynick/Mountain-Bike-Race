package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RaceRepository;
import com.richard.bikeRaceService.repository.RaceResultRepository;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.request.RaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaceServiceImpl implements IRaceService{

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RaceResultRepository raceResultRepository;


    @Override
    public Race registerRace(RaceRequest race) {
        Race newRace = Race.builder()
                .name(race.getName())
                .startTime(race.getStartTime())
                .endTime(race.getEndTime())
                .location(race.getLocation())
                .latitude(race.getLatitude())
                .longitude(race.getLongitude())
                .build();
        return raceRepository.save(newRace);
    }

    @Override
    public RaceResult registerRiderForRace(AddRiderToRaceRequest request) {
        Rider rider = riderRepository.findByName(request.getRideName())
                .orElseThrow(() -> new IllegalArgumentException("Rider not found: " + request.getRideName()));
        Race race = raceRepository.findByName(request.getRaceName())
                .orElseThrow(() -> new IllegalArgumentException("Race not found: " + request.getRaceName()));

        RaceResult raceResult = RaceResult.builder()
                .rider(rider)
                .race(race)
                .status(request.getStatus())
                .build();

        return raceResultRepository.save(raceResult);
    }
}
