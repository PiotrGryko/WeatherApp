package bepoland.piotr.com.bepolandtest.app.model;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bepoland.piotr.com.bepolandtest.R;

/**
 * Created by piotr on 20/05/17.
 */
public class ModelWeather {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
            .getDefault());
    @SerializedName("id")
    int id;
    @SerializedName("main")
    String main;
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
    @SerializedName("grnd_level")
    float grnd_level;
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

    public String getMinTemperature() {
        return Float.toString(temp_min);
    }

    public String getMaxTemperature() {
        return Float.toString(temp_max);
    }

    public String getTemperature() {
        return Float.toString(temp);
    }

    public String getPressure() {
        return Float.toString(pressure);
    }

    public String getHumidity() {
        return Float.toString(humidity);
    }

    public String getSeaLevel() {
        return Float.toString(sea_level);
    }

    public String getWindSpeed() {
        return Float.toString(windSpeed);
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

    public String getImageUrl() {
        return "http://openweathermap.org/img/w/" + imageUrl + ".png";
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.equals(""))
            return;
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
