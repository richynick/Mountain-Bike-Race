package com.richard.bikeRaceService.request;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceRequest {

    private String name;
    private String location;
    private String latitude;
    private String longitude;
    private LocalTime startTime;
    private LocalTime endTime;
}
