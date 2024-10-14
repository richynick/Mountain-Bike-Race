package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.request.RaceRequest;

public interface IRaceService {

    Race registerRace(RaceRequest race);

    public RaceResult registerRiderForRace(AddRiderToRaceRequest request);
}
