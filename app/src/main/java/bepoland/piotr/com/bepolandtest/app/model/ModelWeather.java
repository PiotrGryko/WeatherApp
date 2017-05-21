package bepoland.piotr.com.bepolandtest.app.model;

import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.database.CitiesDatabase;
import bepoland.piotr.com.bepolandtest.util.DownloadImageTask;

/**
 * Created by piotr on 20/05/17.
 */
public class ModelWeather {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
            .getDefault());

    private ModelWeather(int id, String descirption, String imageUrl, float temp, float pressure, float
            humidity, float temp_max, float temp_min, float sea_level, float windSpeed, long
            sunrise, long sunset, long date) {
        this._ID = id;
        this.descirption = descirption; this.imageUrl = imageUrl; this.temp = temp;
        this.pressure = pressure; this.humidity = humidity; this.temp_max = temp_max;
        this.temp_min = temp_min; this.sea_level = sea_level; this.windSpeed = windSpeed;
        this.sunrise = sunrise; this.sunset = sunset; this.date = date;
    }

    @SerializedName("description")
    String descirption;
    @SerializedName("icon")
    String imageUrl;
    @SerializedName("temp")
    float temp;
    @SerializedName("pressure")
    float pressure;
    @SerializedName("humidity")
    float humidity;
    @SerializedName("temp_min")
    float temp_min;
    @SerializedName("temp_max")
    float temp_max;
    @SerializedName("sea_level")
    float sea_level;
    @SerializedName("speed")
    float windSpeed;
    @SerializedName("sunrise")
    long sunrise;
    @SerializedName("sunset")
    long sunset;
    @SerializedName("dt")
    long date;

    private int _ID;
    public int getId()
    {
        return _ID;
    }


    public String getDescirption() {
        return descirption;
    }

    public float getMinTemperature() {
        return temp_min;
    }

    public float getMaxTemperature() {
        return temp_max;
    }

    public float getTemperature() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getSeaLevel() {
        return sea_level;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public String getSunsetDate() {
        return sdf.format(new Date(sunset * 1000L));
    }

    public String getSunriseDate() {
        return sdf.format(new Date(sunrise * 1000L));
    }

    public String getDate() {
        return sdf.format(new Date(date * 1000L));
    }

    public long getRawSunsetDate() {return sunset;}

    public long getRawSunriseDate() {
        return sunrise;
    }

    public long getRawDate() {
        return date;
    }

    public String getImageUrl() {
        return "http://openweathermap.org/img/w/" + imageUrl + ".png";
    }
    public String getRawImageUrl()
    {
        return imageUrl;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.equals("")) return;
        new DownloadImageTask(view,imageUrl).start();
    }

    public static ModelWeather valueOf(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_DESCRIPTION));
        int id= cursor.getInt(cursor.getColumnIndex(CitiesDatabase.Weather._ID));
        float humidity = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_HUMIDITY));
        String imageUrl = cursor.getString(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_IMAGE_URL));
        float pressure = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_PRESSURE));
        float seaLevel = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_SEA_LEVEL));
        long sunset = cursor.getLong(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_SUNRISE));
        long sunrise = cursor.getLong(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_SUNSET));
        long date = cursor.getLong(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_DATE));
        float temp = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_TEMP));
        float tempMin = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_TEMP_MIN));
        float tempMax = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_TEMP_MAX));
        float windSpeed = cursor.getFloat(cursor.getColumnIndex(CitiesDatabase.Weather.COLUMN_WIND_SPEED));

        return new ModelWeather(id,description, imageUrl, temp, pressure, humidity, tempMax, tempMin, seaLevel, windSpeed, sunrise, sunset, date);
    }
}
