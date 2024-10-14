package com.richard.bikeRaceService.controller;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.service.IRaceResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RaceResultController {

    @Autowired
    private IRaceResultService raceResult;

    @PutMapping("/update-result")
    public ResponseEntity<RaceResult> updateRaceResult(@RequestBody AddRiderToRaceRequest request) {
        RaceResult updatedResult = raceResult.updateRaceResult(request);
        return new ResponseEntity<>(updatedResult, HttpStatus.OK);

    }
    @GetMapping("/best-riders/{raceId}")
    public ResponseEntity<List<Rider>> getToRiders(@PathVariable Long raceId){
        List<Rider> bestRiders = raceResult.getTop3Riders(raceId);
        return new ResponseEntity<>(bestRiders, HttpStatus.OK);
    }
    @GetMapping("/{raceName}/dnf")
    public List<RaceResult> getRidersDidNotFinish(@PathVariable Race raceName) {
        return raceResult.getRidersDidNotFinish(raceName);
    }
    @GetMapping("/not-in-race/{raceName}")
    public ResponseEntity<List<Rider>> getRidersNotInRace(@PathVariable String raceName) {
        List<Rider> riders = raceResult.getRidersNotInRace(raceName);
        return ResponseEntity.ok(riders);
    }
}

