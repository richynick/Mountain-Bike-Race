package com.richard.bikeRaceService.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderRequest {
    private String name;
    private String email;
}
