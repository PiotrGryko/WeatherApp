package bepoland.piotr.com.bepolandtest.app.map;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 20/05/17.
 */
public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final SharedPreferences sharedPreferences;
    private final Geocoder geocoder;
    private final DAO dao;

    @Inject
    public MapPresenter(MapContract.View view, SharedPreferences sharedPreferences, Geocoder
            geocoder, DAO dao) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.geocoder = geocoder;
        this.dao = dao;
    }

    @Override
    public void addLocation(LatLng position) {
        String cityName = "";
        try {
            List<Address> result = geocoder.getFromLocation(position.latitude, position
                    .longitude, 1);
            Address address = result.get(0);
            if (address != null) {
                cityName = address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ModelCity city = new ModelCity(position, cityName);
        dao.loadWeather(position, new Response.Listener<ModelWeather>() {

            @Override
            public void onResponse(ModelWeather response) {
                city.setWeather(response);
                view.locationAdded();
                String data = sharedPreferences.getString("data", "[]");
                JSONArray array = null;
                try {
                    array = new JSONArray(data);
                    array.put(new JSONObject(city.toJson()));
                    sharedPreferences.edit().putString("data", array.toString()).commit();

                    ModelCity[] result = new ModelCity[array.length()];
                    for(int i=0;i<array.length();i++)
                    {
                        result[i]=ModelCity.fromJson(array.getJSONObject(i).toString());

                    }
                    view.publishData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                view.locationError();
            }
        });
    }

    @Override
    public void loadData() {
        String data = sharedPreferences.getString("data", "[]");
        try {
            JSONArray array = new JSONArray(data);
            ModelCity[] result = new ModelCity[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                result[i] = ModelCity.fromJson(object.toString());
            }
            view.publishData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
