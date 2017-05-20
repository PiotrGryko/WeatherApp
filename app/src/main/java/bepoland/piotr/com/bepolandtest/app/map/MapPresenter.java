package bepoland.piotr.com.bepolandtest.app.map;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 20/05/17.
 */
public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final SharedPreferences sharedPreferences;
    private final Geocoder geocoder;

    @Inject
    public MapPresenter(MapContract.View view, SharedPreferences sharedPreferences, Geocoder
            geocoder) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.geocoder = geocoder;
    }

    @Override
    public void addLocation(LatLng position) {
        Log.d("XXX", "add location " + position.toString() + " " + sharedPreferences);
        String cityName = "";
        try {
            List<Address> result = geocoder.getFromLocation(position.latitude, position
                    .longitude, 1);
            Address address = result.get(0);
            if (address != null) {
                cityName = address.getLocality();
                Log.d("XXX", "city " + cityName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = sharedPreferences.getString("data", "[]");
        try {
            JSONArray array = new JSONArray(data);
            JSONObject object = new JSONObject();
            object.put("lat", position.latitude);
            object.put("lon", position.longitude);
            object.put("name",cityName);
            array.put(object);
            sharedPreferences.edit().putString("data", array.toString()).commit();
            Log.d("XXX", "data saved " + array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        String data = sharedPreferences.getString("data", "[]");
        try {
            JSONArray array = new JSONArray(data);
            ModelCity[] result = new ModelCity[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("lat");
                double lon = object.getDouble("lon");
                String name = object.has("name") ? object.getString("name") : "";
                result[i] = new ModelCity(new LatLng(lat, lon), name);
            }
            view.publishData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
