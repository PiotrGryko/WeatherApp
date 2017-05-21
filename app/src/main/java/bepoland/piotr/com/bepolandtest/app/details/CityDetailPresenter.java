package bepoland.piotr.com.bepolandtest.app.details;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

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
    public void loadForecast(ModelCity city) {
        dao.loadForecast(new LatLng(city.getLat(), city.getLon()), new Response.Listener<ModelWeather[]>() {

            @Override
            public void onResponse(ModelWeather[] response) {
                contractView.forecastLoaded();
                contractView.publishData(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                contractView.forecastError();
            }
        });

    }
}
