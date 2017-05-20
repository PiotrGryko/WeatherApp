package bepoland.piotr.com.bepolandtest.app.model;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import bepoland.piotr.com.bepolandtest.R;

/**
 * Created by piotr on 20/05/17.
 */
public class ModelWeather {

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
    float wind_speed;
    @SerializedName("sunrise")
    long sunrise;
    @SerializedName("sunset")
    long sunset;

    public String getImageUrl() {
        Log.d("XXX","get image url ");
        return "http://openweathermap.org/img/w/" + imageUrl + ".png";
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        Log.d("XXX","set image url "+imageUrl);

        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
