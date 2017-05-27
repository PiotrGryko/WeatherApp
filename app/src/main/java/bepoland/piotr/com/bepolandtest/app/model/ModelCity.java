package bepoland.piotr.com.bepolandtest.app.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by piotr on 20/05/17.
 */

@Entity(tableName = "city")
public class ModelCity implements Serializable {

    private final static Gson gson = new Gson();


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "lat")
    @SerializedName("lat")
    private double lat;

    @ColumnInfo(name = "lon")
    @SerializedName("lon")
    private double lon;

    @Embedded
    @SerializedName("weather")
    private ModelWeather weather;

    public ModelCity(double lat, double lon, String name) {

        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public double getLat() {

        return this.lat;
    }

    public double getLon() {

        return this.lon;
    }

    public ModelWeather getWeather() {

        return weather;
    }

    public void setWeather(ModelWeather weather) {

        this.weather = weather;
    }

    public String getPosition() {

        return "lat: " + String.format(Locale.getDefault(), "%.2f", lat) + " lon " + String
                .format(Locale.getDefault(), "%.2f", lon);
    }

    public String getName() {

        if (name != null && !name.trim().equals("")) return name;
        else return getPosition();
    }

    public String toJson() {

        return gson.toJson(this, ModelCity.class);
    }

    public static ModelCity fromJson(String json) {

        return gson.fromJson(json, ModelCity.class);
    }

    public String getFormattedImageUrl() {

        if (weather != null) return "http://openweathermap.org/img/w/" + weather.imageUrl + ".png";
        else return null;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {

        if (imageUrl == null || imageUrl.trim().equals("")) return;
        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }
}
