package com.richard.weather.model;

import com.richard.weather.utility.Constant;
import org.json.JSONObject;

public class FormatResponse {

    public JSONObject formatJSONResponse(Weather weather) {

        JSONObject jsonResp = new JSONObject();

        jsonResp.put(Constant.JSON_ID, weather.getCityId());
        jsonResp.put(Constant.JSON_NAME, weather.getCityName());
        jsonResp.put(Constant.JSON_WEATHER, weather.getWeather());
        jsonResp.put(Constant.JSON_MAIN_TEMP, weather.getTemp());

        return jsonResp;
    }

    public String formatWeatherListToString(WeatherDetails weatherDetails) {

        String formattedString = "";

        formattedString += weatherDetails.getWeatherInstance(0).getWeatherDescription();
        for (int i = 1; i < weatherDetails.getWeatherCount(); i++) {
            formattedString += ", " + weatherDetails.getWeatherInstance(i).getWeatherDescription();
        }

        return formattedString;
    }
}
