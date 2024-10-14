package com.richard.weather.repository;

import com.richard.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwmRepository extends JpaRepository<Weather, Long> {
}
