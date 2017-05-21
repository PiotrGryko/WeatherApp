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

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 12/05/17.
 */
public class CityListPresenter implements CityListContract.Presenter {

    private String TAG = CityListPresenter.class.getName();
    DatabaseHelper databaseHelper;
    CityListContract.View view;

    @Inject
    public CityListPresenter(DatabaseHelper databaseHelper, CityListContract.View view) {
        this.databaseHelper=databaseHelper;
        this.view = view;
    }

    @Override
    public void loadData() {
        view.publishData(databaseHelper.getCities());
    }
}
