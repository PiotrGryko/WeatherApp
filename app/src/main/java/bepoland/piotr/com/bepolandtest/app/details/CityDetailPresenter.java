package bepoland.piotr.com.bepolandtest.app.details;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 17/05/17.
 */

public class CityDetailPresenter implements CityDetailContract.Presenter {

    DAO dao;
    CityDetailContract.View contractView;

    @Inject
    public CityDetailPresenter(DAO dao, CityDetailContract.View view) {
        this.dao = dao;
        this.contractView = view;
    }

    @Override
    public void loadData(ModelCity city) {
        dao.loadWeather(city.getLatLng(), new Response.Listener<ModelWeather>() {

            @Override
            public void onResponse(ModelWeather response) {
                Log.d("xxx","result ");
                contractView.publishData(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("XXX",error.toString());
            }
        });

    }
}
