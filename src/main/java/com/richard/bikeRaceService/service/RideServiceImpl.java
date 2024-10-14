package com.richard.bikeRaceService.service;

import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.RiderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImpl implements IRideService{

    @Autowired
    private RiderRepository riderRepository;


    @Override
    public Rider registerRiders(RiderRequest rider) {
        Rider newRider = Rider.builder()
                .name(rider.getName())
                .email(rider.getEmail())
                .build();
        return riderRepository.save(newRider);
    }
}
