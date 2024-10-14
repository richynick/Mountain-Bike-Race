package com.richard.weather.service;

import com.richard.bikeRaceService.model.Rider;
import com.richard.bikeRaceService.repository.RiderRepository;
import com.richard.bikeRaceService.request.RiderRequest;
import com.richard.bikeRaceService.service.RideServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private RideServiceImpl riderService = new RideServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRideSuccessfully() {
        RiderRequest riderRequest = new RiderRequest();
        riderRequest.setName("John Doe");
        riderRequest.setEmail("john@example.com");
        Rider rider = Rider.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();
        when(riderRepository.save(any(Rider.class))).thenReturn(rider);

        Rider result = riderService.registerRiders(riderRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }
}
