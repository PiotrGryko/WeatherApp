package bepoland.piotr.com.bepolandtest.app.list;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 12/05/17.
 */
public class CityListPresenter implements CityListContract.Presenter {

    private String TAG = CityListPresenter.class.getName();
    SharedPreferences preferences;
    CityListContract.View view;

    @Inject
    public CityListPresenter(SharedPreferences preferences, CityListContract.View view) {
        this.preferences = preferences;
        this.view = view;
    }

    @Override
    public void loadData() {
        String data = preferences.getString("data", "[]");
        try {
            JSONArray array = new JSONArray(data);
            ModelCity[] result = new ModelCity[array.length()];
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("lat");
                double lon = object.getDouble("lon");
                String name = object.has("name")?object.getString("name"):"";
                result[i] = new ModelCity(new LatLng(lat, lon), name);
            }
            view.publishData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
