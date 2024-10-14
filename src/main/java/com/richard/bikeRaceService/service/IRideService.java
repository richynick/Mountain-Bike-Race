package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.request.RiderRequest;

public interface IRideService {

    Rider registerRiders(RiderRequest rider);
}
