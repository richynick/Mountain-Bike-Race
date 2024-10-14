package com.richard.weather.service;

import com.richard.bikeRaceService.model.Race;
import com.richard.bikeRaceService.model.RaceResult;
import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RaceRepository;
import com.richard.bikeRaceService.repository.RaceResultRepository;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.AddRiderToRaceRequest;
import com.richard.bikeRaceService.request.RaceRequest;
import com.richard.bikeRaceService.service.RaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RiderRepository riderRepository;

    @Mock
    private RaceResultRepository raceResultRepository;

    @InjectMocks
    private RaceServiceImpl raceService = new RaceServiceImpl();



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterRace() {
        RaceRequest raceRequest = new RaceRequest();
        raceRequest.setName("Test Race");
        raceRequest.setStartTime(LocalTime.of(14, 30, 15));
        raceRequest.setEndTime(LocalTime.of(22,30,15));
        raceRequest.setLocation("Test Location");

        Race savedRace = Race.builder()
                .id(1L)
                .name("Test Race")
                .startTime(LocalTime.of(14, 30, 15))
                .endTime(LocalTime.of(22,30,15))
                .location("Test Location")
                .build();

        when(raceRepository.save(any(Race.class))).thenReturn(savedRace);


        Race newRace = raceService.registerRace(raceRequest);

        ArgumentCaptor<Race> raceCaptor = ArgumentCaptor.forClass(Race.class);
        verify(raceRepository).save(raceCaptor.capture());

        Race capturedRace = raceCaptor.getValue();
        assertThat(capturedRace.getName()).isEqualTo("Test Race");
        assertThat(capturedRace.getStartTime()).isEqualTo(LocalTime.of(14, 30, 15));
        assertThat(capturedRace.getEndTime()).isEqualTo(LocalTime.of(22,30,15));
        assertThat(capturedRace.getLocation()).isEqualTo("Test Location");

        assertThat(newRace).isNotNull();
        assertThat(newRace.getId()).isEqualTo(1L);

    }

    @Test
    void testRegisterRiderForRaceSuccessfully() {
        // Arrange: Create request and mock objects
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("John Doe");
        request.setRaceName("Mountain Challenge");
        request.setStatus("Registered");

        Rider mockRider = Rider.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        Race mockRace = Race.builder()
                .id(1L)
                .name("Mountain Challenge")
                .startTime(LocalTime.of(14, 30, 15))
                .endTime(LocalTime.of(22,30,15))
                .location("Mountain Trails")
                .build();

        RaceResult savedRaceResult = RaceResult.builder()
                .id(1L)
                .rider(mockRider)
                .race(mockRace)
                .status("Registered")
                .build();

        // Mock repository responses
        when(riderRepository.findByName("John Doe")).thenReturn(Optional.of(mockRider));
        when(raceRepository.findByName("Mountain Challenge")).thenReturn(Optional.of(mockRace));
        when(raceResultRepository.save(any(RaceResult.class))).thenReturn(savedRaceResult);

        // Act: Call the method and get the result
        RaceResult result = raceService.registerRiderForRace(request);

        // Assert: Verify the result
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRider().getName()).isEqualTo("John Doe");
        assertThat(result.getRace().getName()).isEqualTo("Mountain Challenge");
        assertThat(result.getStatus()).isEqualTo("Registered");

        // Use ArgumentCaptor to verify the RaceResult input to save()
        ArgumentCaptor<RaceResult> raceResultCaptor = ArgumentCaptor.forClass(RaceResult.class);
        verify(raceResultRepository).save(raceResultCaptor.capture());

        RaceResult capturedRaceResult = raceResultCaptor.getValue();
        assertThat(capturedRaceResult.getRider()).isEqualTo(mockRider);
        assertThat(capturedRaceResult.getRace()).isEqualTo(mockRace);
        assertThat(capturedRaceResult.getStatus()).isEqualTo("Registered");

        // Verify repository interactions
        verify(riderRepository).findByName("John Doe");
        verify(raceRepository).findByName("Mountain Challenge");
    }


    @Test
    void testRegisterRiderForRace_RiderNotFound() {
        // Arrange: Create request with non-existent rider
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("Unknown Rider");
        request.setRaceName("Mountain Challenge");
        request.setStatus("Registered");

        when(riderRepository.findByName("Unknown Rider")).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when rider is not found
        assertThatThrownBy(() -> raceService.registerRiderForRace(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Rider not found: Unknown Rider");

        verify(riderRepository).findByName("Unknown Rider");
        verifyNoInteractions(raceRepository, raceResultRepository);  // No further calls should happen
    }

    @Test
    void testRegisterRiderForRace_RaceNotFound() {
        // Arrange: Create request with non-existent race
        AddRiderToRaceRequest request = new AddRiderToRaceRequest();
        request.setRideName("John Doe");
        request.setRaceName("Unknown Race");
        request.setStatus("Registered");

        Rider mockRider = Rider.builder().id(1L).name("John Doe").build();

        when(riderRepository.findByName("John Doe")).thenReturn(Optional.of(mockRider));
        when(raceRepository.findByName("Unknown Race")).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when race is not found
        assertThatThrownBy(() -> raceService.registerRiderForRace(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Race not found: Unknown Race");

        verify(riderRepository).findByName("John Doe");
        verify(raceRepository).findByName("Unknown Race");
        verifyNoInteractions(raceResultRepository);  // RaceResult should not be saved
    }
}
