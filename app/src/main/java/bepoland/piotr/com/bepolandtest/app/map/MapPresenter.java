package bepoland.piotr.com.bepolandtest.app.map;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.DAO;
import bepoland.piotr.com.bepolandtest.util.HttpRequestTask;

/**
 * Created by piotr on 20/05/17.
 */
public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final Geocoder geocoder;
    private final DAO dao;
    private final DatabaseHelper databaseHelper;

    public MapPresenter(MapContract.View view, Geocoder
            geocoder, DAO dao, DatabaseHelper databaseHelper) {
        this.view = view;
        this.geocoder = geocoder;
        this.dao = dao;
        this.databaseHelper = databaseHelper;
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
        dao.loadWeather(position, new HttpRequestTask.OnRequestListener<ModelWeather>() {

            @Override
            public void onSuccess(ModelWeather data) {
                city.setWeather(data);
                databaseHelper.saveCity(city);
                view.locationAdded();
                loadData();
            }

            @Override
            public void onError(Exception error) {
                view.locationError();
            }
        });
    }

    @Override
    public void loadData() {
        view.publishData(databaseHelper.getCities());
    }
}
