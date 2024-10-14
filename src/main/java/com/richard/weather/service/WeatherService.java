package com.richard.weather.service;

import org.json.JSONArray;
import org.json.JSONObject;

public interface WeatherService {


    JSONObject getWeatherDetailsByCityName(String cityName);
    JSONArray getWeatherDetailsByCityId(String cityId, String appId);
}
