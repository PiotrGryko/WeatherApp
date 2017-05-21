package bepoland.piotr.com.bepolandtest.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;

/**
 * Created by piotr on 10/05/17.
 */
public class DAO {

    private String baseUrl;
    private final String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";

    public DAO(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void loadWeather(LatLng position, HttpRequestTask.OnRequestListener<ModelWeather> successListener) {
        String url = baseUrl + "weather?lat=" + position.latitude + "&lon=" + position.longitude +
                "&appid=" + API_KEY + "&units=metric";
        Log.d("XXX","load weather "+url);
        GsonRequest<ModelWeather> request = new GsonRequest<ModelWeather>(
                url, ModelWeather.class, successListener);
        request.execute();
    }

    public void loadForecast(LatLng position, HttpRequestTask.OnRequestListener<ModelWeather[]> successListener) {
        String url = baseUrl + "forecast?lat=" + position.latitude + "&lon=" + position.longitude +
                "&appid=" + API_KEY + "&units=metric";
        GsonRequest<ModelWeather[]> request = new GsonRequest<ModelWeather[]>(url, ModelWeather[].class, successListener) {

            public String deserialize(String data) {
                JSONArray result = new JSONArray();
                JSONObject response = null;
                try {
                    response = new JSONObject(data);
                    JSONArray list = response.has("list") ? response.getJSONArray("list") : new
                            JSONArray();
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject listElement = list.getJSONObject(i);
                        JSONObject flatElement = new JSONObject(super.deserialize(listElement
                                .toString()));
                        result.put(flatElement);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("XXX", "forecast result " + result.toString());
                return result.toString();
            }
        };
        request.execute();
    }
}
