package com.richard.bikeRaceService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
    private LocalTime finishTime;
    private String status;
}
