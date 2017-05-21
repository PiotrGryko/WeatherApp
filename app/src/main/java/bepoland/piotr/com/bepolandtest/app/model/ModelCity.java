package bepoland.piotr.com.bepolandtest.app.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Locale;

import bepoland.piotr.com.bepolandtest.app.database.CitiesDatabase;

/**
 * Created by piotr on 20/05/17.
 */
public class ModelCity implements Serializable {

    private final static Gson gson = new Gson();
    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("weather")
    private ModelWeather weather;

    public ModelCity(LatLng position, String name) {
        this.lat = position.latitude;
        this.lon = position.longitude;
        this.name = name;
    }


    public void setWeather(ModelWeather weather) {
        this.weather = weather;
    }

    public String getName() {
        if (name != null && !name.trim().equals(""))
            return name;
        else return getPosition();
    }

    public String getPosition() {
        return "lat: " + String.format(Locale.getDefault(), "%.2f", lat) + " lon " + String
                .format(Locale.getDefault(), "%.2f", lon);
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

    public String toJson() {
        return gson.toJson(this, ModelCity.class);
    }

    public static ModelCity fromJson(String json) {
        return gson.fromJson(json, ModelCity.class);
    }

    public String getImageUrl() {
        if (weather != null)
            return "http://openweathermap.org/img/w/" + weather.imageUrl + ".png";
        else return null;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        if (imageUrl == null || imageUrl.trim().equals(""))
            return;
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public static ModelCity valueOf(Cursor cursor)
    {
        String name = cursor.getString(cursor.getColumnIndex(CitiesDatabase.City.COLUMN_NAME));
        double lat = cursor.getDouble(cursor.getColumnIndex(CitiesDatabase.City.COLUMN_LAT));
        double lon = cursor.getDouble(cursor.getColumnIndex(CitiesDatabase.City.COLUMN_LON));
        ModelCity modelCity =  new ModelCity(new LatLng(lat,lon),name);
        ModelWeather modelWeather = ModelWeather.valueOf(cursor);
        modelCity.setWeather(modelWeather);
        return modelCity;
    }
}
