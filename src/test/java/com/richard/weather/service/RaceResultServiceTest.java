package com.richard.weather.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RaceRepository;
import com.richard.bikeRaceService.repository.RaceResultRepository;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.service.RaceResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RaceResultServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RaceResultRepository raceResultRepository;

    @InjectMocks
    private RaceResultServiceImpl raceResultService;

    private Race race;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateRaceResultSuccessfully() {
        // Arrange: Create request and mock objects
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("John Doe");
        request.setRaceName("Mountain Challenge");
        request.setStatus("Completed");
        request.setFinishTime(LocalTime.of(12, 30)); // Example finish time

        Rider mockRider = Rider.builder().id(1L).name("John Doe").build();
        Race mockRace = Race.builder().id(1L).name("Mountain Challenge").build();

        RaceResult existingRaceResult = RaceResult.builder()
                .id(1L)
                .rider(mockRider)
                .race(mockRace)
                .status("Registered")
                .build();

        RaceResult updatedRaceResult = RaceResult.builder()
                .id(1L)
                .rider(mockRider)
                .race(mockRace)
                .status("Completed")
                .finishTime(LocalTime.of(12, 30))
                .build();

        // Mock repository responses
        when(riderRepository.findByName("John Doe")).thenReturn(Optional.of(mockRider));
        when(raceRepository.findByName("Mountain Challenge")).thenReturn(Optional.of(mockRace));
        when(raceResultRepository.findByRiderAndRace(mockRider, mockRace)).thenReturn(Optional.of(existingRaceResult));
        when(raceResultRepository.save(any(RaceResult.class))).thenReturn(updatedRaceResult);

        // Act: Call the method and get the result
        RaceResult result = raceResultService.updateRaceResult(request);

        // Assert: Verify the result
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("Completed");
        assertThat(result.getFinishTime()).isEqualTo(LocalTime.of(12, 30));

        // Use ArgumentCaptor to verify the update on the RaceResult
        ArgumentCaptor<RaceResult> raceResultCaptor = ArgumentCaptor.forClass(RaceResult.class);
        verify(raceResultRepository).save(raceResultCaptor.capture());

        RaceResult capturedRaceResult = raceResultCaptor.getValue();
        assertThat(capturedRaceResult.getStatus()).isEqualTo("Completed");
        assertThat(capturedRaceResult.getFinishTime()).isEqualTo(LocalTime.of(12, 30));

        // Verify repository interactions
        verify(riderRepository).findByName("John Doe");
        verify(raceRepository).findByName("Mountain Challenge");
        verify(raceResultRepository).findByRiderAndRace(mockRider, mockRace);
    }

    @Test
    void testUpdateRaceResult_RiderNotFound() {
        // Arrange: Create request with non-existent rider
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("Unknown Rider");
        request.setRaceName("Mountain Challenge");

        when(riderRepository.findByName("Unknown Rider")).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when rider is not found
        assertThatThrownBy(() -> raceResultService.updateRaceResult(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rider not found: Unknown Rider");

        verify(riderRepository).findByName("Unknown Rider");
        verifyNoInteractions(raceRepository, raceResultRepository);  // No further calls should occur

    }

    @Test
    void testUpdateRaceResult_RaceNotFound() {
        // Arrange: Create request with non-existent race
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("John Doe");
        request.setRaceName("Unknown Race");

        Rider mockRider = Rider.builder().id(1L).name("John Doe").build();
        when(riderRepository.findByName("John Doe")).thenReturn(Optional.of(mockRider));
        when(raceRepository.findByName("Unknown Race")).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when race is not found
        assertThatThrownBy(() -> raceResultService.updateRaceResult(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Race not found: Unknown Race");

        verify(riderRepository).findByName("John Doe");
        verify(raceRepository).findByName("Unknown Race");
        verifyNoInteractions(raceResultRepository);  // No further interactions
    }

    @Test
    void testUpdateRaceResult_RaceResultNotFound() {
        // Arrange: Create request with non-existent race result
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("John Doe");
        request.setRaceName("Mountain Challenge");

        Rider mockRider = Rider.builder().id(1L).name("John Doe").build();
        Race mockRace = Race.builder().id(1L).name("Mountain Challenge").build();

        when(riderRepository.findByName("John Doe")).thenReturn(Optional.of(mockRider));
        when(raceRepository.findByName("Mountain Challenge")).thenReturn(Optional.of(mockRace));
        when(raceResultRepository.findByRiderAndRace(mockRider, mockRace)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when race result is not found
        assertThatThrownBy(() -> raceResultService.updateRaceResult(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Race result not found for rider: John Doe");

        verify(riderRepository).findByName("John Doe");
        verify(raceRepository).findByName("Mountain Challenge");
        verify(raceResultRepository).findByRiderAndRace(mockRider, mockRace);
    }

    @Test
    void testGetTop3Riders_Successful() {
        // Arrange: Mock race results
        Rider rider1 = Rider.builder().id(1L).name("Rider One").build();
        Rider rider2 = Rider.builder().id(2L).name("Rider Two").build();
        Rider rider3 = Rider.builder().id(3L).name("Rider Three").build();
        Rider rider4 = Rider.builder().id(4L).name("Rider Four").build(); // Extra rider

        RaceResult result1 = RaceResult.builder().rider(rider1).finishTime(LocalTime.of(1, 30)).build();
        RaceResult result2 = RaceResult.builder().rider(rider2).finishTime(LocalTime.of(1, 40)).build();
        RaceResult result3 = RaceResult.builder().rider(rider3).finishTime(LocalTime.of(1, 50)).build();
        RaceResult result4 = RaceResult.builder().rider(rider4).finishTime(LocalTime.of(2, 0)).build();

        List<RaceResult> mockResults = Arrays.asList(result1, result2, result3, result4);

        // Mock repository response
        when(raceResultRepository.findByRaceIdOrderByFinishTime(1L)).thenReturn(mockResults);

        // Act: Call the method
        List<Rider> top3Riders = raceResultService.getTop3Riders(1L);

        // Assert: Verify the results
        assertThat(top3Riders).hasSize(3);
        assertThat(top3Riders).containsExactly(rider1, rider2, rider3);

        // Verify interaction with the repository
        verify(raceResultRepository).findByRaceIdOrderByFinishTime(1L);
    }

    @Test
    void testGetTop3Riders_NoRiders() {
        // Arrange: Mock empty race results
        when(raceResultRepository.findByRaceIdOrderByFinishTime(3L)).thenReturn(Collections.emptyList());

        // Act: Call the method
        List<Rider> top3Riders = raceResultService.getTop3Riders(3L);

        // Assert: Verify the result is empty
        assertThat(top3Riders).isEmpty();

        // Verify interaction with the repository
        verify(raceResultRepository).findByRaceIdOrderByFinishTime(3L);
    }

    @Test
    void testGetRidersDidNotFinish_Success() {
        // Arrange: Mock race results with "DNF" status
        Rider rider1 = Rider.builder().id(1L).name("Rider One").build();
        Rider rider2 = Rider.builder().id(2L).name("Rider Two").build();

        RaceResult result1 = RaceResult.builder().rider(rider1).race(race).status("DNF").build();
        RaceResult result2 = RaceResult.builder().rider(rider2).race(race).status("DNF").build();

        List<RaceResult> mockResults = Arrays.asList(result1, result2);

        // Mock the repository call
        when(raceResultRepository.findByRaceAndStatus(race, "DNF")).thenReturn(mockResults);

        // Act: Call the method under test
        List<RaceResult> dnfRiders = raceResultService.getRidersDidNotFinish(race);

        // Assert: Verify the results
        assertThat(dnfRiders).hasSize(2);
        assertThat(dnfRiders).containsExactly(result1, result2);

        // Verify the interaction with the mock repository
        verify(raceResultRepository).findByRaceAndStatus(race, "DNF");
    }

    @Test
    void testGetRidersNotInRace_Success() {
        // Arrange: Create mock riders not participating in the race
        Rider rider1 = Rider.builder().id(1L).name("Alice").build();
        Rider rider2 = Rider.builder().id(2L).name("Bob").build();

        List<Rider> mockRiders = Arrays.asList(rider1, rider2);

        // Mock the repository response
        when(riderRepository.findRidersNotInRace("Mountain Race")).thenReturn(mockRiders);

        // Act: Call the method under test
        List<Rider> result = raceResultService.getRidersNotInRace("Mountain Race");

        // Assert: Verify the results
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(rider1, rider2);

        // Verify interaction with the mock repository
        verify(riderRepository).findRidersNotInRace("Mountain Race");
    }
}
