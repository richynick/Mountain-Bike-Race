package com.richard.weather.model;

import com.richard.weather.utility.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class AbstractWeather extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    private int weatherCount;
    private List<Weather> weatherList;

    AbstractWeather(){
        super();

        weatherCount = 0;
        weatherList = Collections.emptyList();
    }

    AbstractWeather(JSONObject jsonObj) {
        super(jsonObj);

        JSONArray weatherArray = (jsonObj != null) ? jsonObj.optJSONArray(Constant.JSON_WEATHER) : new JSONArray();
        this.weatherList = (weatherArray != null) ? new ArrayList<Weather>(weatherArray.length()) : Collections.emptyList();

        if (weatherArray != null && this.weatherList != Collections.EMPTY_LIST) {
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherObj = weatherArray.optJSONObject(i);
                if (weatherObj != null) {
                    this.weatherList.add(new Weather(weatherObj));
                }
            }
        }
        this.weatherCount = this.weatherList.size();
    }

    public Weather getWeatherInstance(int index) {
        return this.weatherList.get(index);
    }

    public int getWeatherCount() {
        return this.weatherCount;
    }

    public static class Main implements Serializable {

        private static final long serialVersionUID = 1L;

        private final float temp;

        Main(){
            super();
            this.temp = Float.NaN;
        }

        Main(JSONObject jsonObj) {
            this.temp = (jsonObj != null) ? jsonObj.optFloat(Constant.JSON_MAIN_TEMP) : Float.NaN;
        }

        public float getTemp() {
            return this.temp;
        }

    }

    public static class Weather implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String description;

        Weather(){
            this.description = null;
        }

        Weather(JSONObject jsonObj) {
            this.description = (jsonObj != null) ? jsonObj.optString(Constant.JSON_WEATHER_DESC) : null;
        }

        public String getWeatherDescription() {
            return this.description;
        }

    }

}
