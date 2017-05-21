package bepoland.piotr.com.bepolandtest.app.database;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piotr on 23/02/17.
 */
public class CitiesDatabase {

    private CitiesDatabase() {
    }

    public static class City implements BaseColumns {

        public static final String TABLE_NAME = "city";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LON = "lon";

    }

    public static class Weather implements BaseColumns {

        public static final String TABLE_NAME = "weather";
        public static final String CITY_ID= "city_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE_URL = "image_rul";
        public static final String COLUMN_TEMP = "temperature";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_TEMP_MIN = "temp_min";
        public static final String COLUMN_TEMP_MAX = "temp_max";
        public static final String COLUMN_SEA_LEVEL = "sea_level";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_SUNRISE = "sunrise";
        public static final String COLUMN_SUNSET = "sunset";
        public static final String COLUMN_DATE = "date";
    }
}
