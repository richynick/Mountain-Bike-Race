package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;

import java.util.List;

public interface IRaceResultService {

    RaceResult updateRaceResult(AddRiderToRaceRequest request);

    List<Rider> getTop3Riders(Long raceId);
    List<RaceResult> getRidersDidNotFinish(Race raceName);
    List<Rider> getRidersNotInRace(String raceName);
}
