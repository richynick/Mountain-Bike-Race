package com.richard.weather.model;

import com.richard.weather.utility.Constant;
import org.json.JSONObject;

public class WeatherDetails extends AbstractWeather {

    private static final long serialVersionUID = 1L;

    private final long cityId;
    private final String cityName;
    private final Main main;

    public WeatherDetails(JSONObject jsonObj) {
        super(jsonObj);

        this.cityId = jsonObj.optLong(Constant.JSON_ID, Long.MIN_VALUE);
        this.cityName = jsonObj.optString(Constant.JSON_NAME, null);

        JSONObject mainObj = (jsonObj != null) ? jsonObj.optJSONObject(Constant.JSON_MAIN) : null;
        this.main = (jsonObj != null) ? new Main(mainObj) : new Main();
    }

    public long getCityId() {
        return this.cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Main getMainInstance() {
        return this.main;
    }

    public static class Main extends AbstractWeather.Main {

        private static final long serialVersionUID = 1L;

        Main(){
            super();
        }

        Main(JSONObject jsonObj) {
            super(jsonObj);
        }

    }

}
