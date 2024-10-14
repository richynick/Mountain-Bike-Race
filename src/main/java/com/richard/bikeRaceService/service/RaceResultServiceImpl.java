package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RaceRepository;
import com.richard.bikeRaceService.repository.RaceResultRepository;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RaceResultServiceImpl implements IRaceResultService{

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RaceResultRepository raceResultRepository;

    @Override
    public RaceResult updateRaceResult(AddRiderToRaceRequest request) {
        Rider rider = riderRepository.findByName(request.getRideName())
                .orElseThrow(() -> new IllegalArgumentException("Rider not found: " + request.getRideName()));

        Race race = raceRepository.findByName(request.getRaceName())
                .orElseThrow(() -> new IllegalArgumentException("Race not found: " + request.getRaceName()));

        RaceResult raceResult = raceResultRepository.findByRiderAndRace(rider,race)
                .orElseThrow(() -> new IllegalArgumentException("Race result not found for rider: " + request.getRideName()));

        raceResult.setStatus(request.getStatus());
        raceResult.setFinishTime(request.getFinishTime());

        return raceResultRepository.save(raceResult);
    }

    @Override
    public List<Rider> getTop3Riders(Long raceId) {
        List<RaceResult> raceResults = raceResultRepository.findByRaceIdOrderByFinishTime(raceId);
        List<Rider> fastestRiders = new ArrayList<>();
        for (int i = 0; i < 3 && i < raceResults.size(); i++) {
            fastestRiders.add(raceResults.get(i).getRider());
        }
        return fastestRiders;
    }

    @Override
    public List<RaceResult> getRidersDidNotFinish(Race raceName) {
        return raceResultRepository.findByRaceAndStatus(raceName, "DNF");
    }

    @Override
    public List<Rider> getRidersNotInRace(String raceName) {
        return riderRepository.findRidersNotInRace(raceName);
    }
}
