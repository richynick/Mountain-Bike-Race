package com.richard.bikeRaceService.controller;

import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.request.RiderRequest;
import com.richard.bikeRaceService.service.IRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

    @Autowired
    private IRideService riderService;

    @PostMapping("/add")
    public ResponseEntity<Rider> registerRider(@RequestBody RiderRequest rider){
        Rider newRider = riderService.registerRiders(rider);
        return new ResponseEntity<>(newRider, HttpStatus.CREATED);
    }
}
