package bepoland.piotr.com.bepolandtest.app.model;

import android.arch.persistence.room.Dao;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by piotr on 20/05/17.
 */

public class ModelWeather {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
            .getDefault());

    public ModelWeather(String descirption, String imageUrl, float temperature, float pressure,
                        float humidity, float maxTemperature, float minTemperature, float
                                seaLevel, float windSpeed, long sunrise, long sunset, long date) {

        this.descirption = descirption;
        this.imageUrl = imageUrl;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.seaLevel = seaLevel;
        this.windSpeed = windSpeed;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.date = date;
    }

    @SerializedName("description")
    String descirption;
    @SerializedName("icon")
    String imageUrl;
    @SerializedName("temp")
    float temperature;
    @SerializedName("pressure")
    float pressure;
    @SerializedName("humidity")
    float humidity;
    @SerializedName("temp_min")
    float minTemperature;
    @SerializedName("temp_max")
    float maxTemperature;
    @SerializedName("seaLevel")
    float seaLevel;
    @SerializedName("speed")
    float windSpeed;
    @SerializedName("sunrise")
    long sunrise;
    @SerializedName("sunset")
    long sunset;
    @SerializedName("dt")
    long date;

    public String getDescirption() {

        return descirption;
    }

    public float getMinTemperature() {

        return minTemperature;
    }

    public float getMaxTemperature() {

        return maxTemperature;
    }

    public float getTemperature() {

        return temperature;
    }

    public float getPressure() {

        return pressure;
    }

    public float getHumidity() {

        return humidity;
    }

    public float getSeaLevel() {

        return seaLevel;
    }

    public float getWindSpeed() {

        return windSpeed;
    }

    public String getFormattedSunset() {

        return sdf.format(new Date(sunset * 1000L));
    }

    public String getFormattedSunrise() {

        return sdf.format(new Date(sunrise * 1000L));
    }

    public String getFormattedDate() {

        return sdf.format(new Date(date * 1000L));
    }

    public long getSunset() {return sunset;}

    public long getSunrise() {

        return sunrise;
    }

    public long getDate() {

        return date;
    }

    public String getFormattedImageUrl() {

        return "http://openweathermap.org/img/w/" + imageUrl + ".png";
    }

    public String getImageUrl() {

        return imageUrl;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {

        if (imageUrl == null || imageUrl.equals("")) return;
        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }
}
