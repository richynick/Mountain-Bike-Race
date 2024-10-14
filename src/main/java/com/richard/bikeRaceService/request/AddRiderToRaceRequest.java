package com.richard.bikeRaceService.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRiderToRaceRequest {

    private String rideName;
    private String raceName;
    private String status;
    private LocalTime finishTime;
}
