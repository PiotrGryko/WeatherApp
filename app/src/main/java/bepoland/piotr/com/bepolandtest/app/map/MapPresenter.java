package bepoland.piotr.com.bepolandtest.app.map;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by piotr on 20/05/17.
 */
public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final Geocoder geocoder;
    private final WeatherApi weatherApi;
    private final CitiesRoomHelper citiesRoomHelper;
    //private final CitiesRoomDatabase citiesRoomDatabase;

    @Inject
    public MapPresenter(MapContract.View view, Geocoder
            geocoder, WeatherApi weatherApi, CitiesRoomHelper citiesRoomHelper) {
        this.view = view;
        this.geocoder = geocoder;
        this.weatherApi=weatherApi;
        this.citiesRoomHelper=citiesRoomHelper;
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
        final ModelCity city = new ModelCity(position.latitude,position.longitude, cityName);

        weatherApi.getWeather(position.latitude,position.longitude,"c6e381d8c7ff98f0fee43775817cf6ad").enqueue(new Callback<ModelWeather>() {

            @Override
            public void onResponse(Call<ModelWeather> call, retrofit2.Response<ModelWeather> response) {
                city.setWeather(response.body());
                citiesRoomHelper.saveData(city, new CitiesRoomHelper.OnOperationFinished<Long>() {

                    @Override
                    public void onFinished(Long result) {
                        view.locationAdded();
                        loadData();
                    }
                });


            }

            @Override
            public void onFailure(Call<ModelWeather> call, Throwable t) {
                view.locationError();
            }
        });

    }

    @Override
    public void loadData() {
        Log.d("XXX","load data map fragment ");
        citiesRoomHelper.loadData(new CitiesRoomHelper.OnOperationFinished<ModelCity[]>() {
            @Override
            public void onFinished(ModelCity[] result) {
                Log.d("XXX","data loaded "+result.length);
                view.publishData(result);
            }
        });
        //view.publishData(citiesRoomDatabase.citiesDao().getCities());
        //view.publishData(databaseHelper.getCities());
    }
}
