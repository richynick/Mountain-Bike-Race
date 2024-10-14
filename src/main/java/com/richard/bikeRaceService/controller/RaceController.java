package com.richard.bikeRaceService.controller;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.request.RaceRequest;
import com.richard.bikeRaceService.service.IRaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/race")
public class RaceController {

    @Autowired
    private IRaceService raceService;

    @PostMapping("/register-race")
    public ResponseEntity<Race> addRace(@Validated @RequestBody RaceRequest race){
        Race newRace = raceService.registerRace(race);
        return new ResponseEntity<>(newRace, HttpStatus.CREATED);
    }

    @PostMapping("/add-rider")
    public ResponseEntity<RaceResult> registerRiderForRace(@RequestBody AddRiderToRaceRequest request){
        RaceResult raceResult = raceService.registerRiderForRace(request);
        return new ResponseEntity<>(raceResult, HttpStatus.CREATED);
    }
}
