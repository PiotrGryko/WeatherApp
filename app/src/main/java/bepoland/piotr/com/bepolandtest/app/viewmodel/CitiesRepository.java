package bepoland.piotr.com.bepolandtest.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by piotr on 27/05/17.
 */

public class CitiesRepository {

    private final CitiesRoomHelper citiesRoomHelper;
    private final WeatherApi weatherApi;
    private final Geocoder geocoder;



    @Inject
    public CitiesRepository(CitiesRoomHelper citiesRoomHelper, WeatherApi weatherApi, Geocoder geocoder) {

        this.citiesRoomHelper = citiesRoomHelper;
        this.weatherApi=weatherApi;
        this.geocoder=geocoder;
    }

    public MutableLiveData<ModelCity[]> loadCities() {

        final MutableLiveData<ModelCity[]> cities= new MutableLiveData<>();
        citiesRoomHelper.loadData(new CitiesRoomHelper.OnOperationFinished<ModelCity[]>() {

            @Override
            public void onFinished(ModelCity[] result) {
                cities.setValue(result);
            }
        });

        return cities;
    }

    public LiveData<ModelCity> addLocation(LatLng position) {
        final MutableLiveData<ModelCity> liveCity = new MutableLiveData<>();

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
                        liveCity.setValue(city);
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelWeather> call, Throwable t) {
                liveCity.setValue(null);
            }
        });

        return liveCity;
    }

    public LiveData<ModelCity> removeElement(final ModelCity city) {
        final MutableLiveData<ModelCity> liveCity = new MutableLiveData<>();

        this.citiesRoomHelper.removeData(city, new CitiesRoomHelper.OnOperationFinished<Integer>() {

            @Override
            public void onFinished(Integer result) {
                liveCity.setValue(city);

            }
        });

        return liveCity;
    }

    public LiveData<ModelWeather[]> loadForecast(ModelCity city) {
        final MutableLiveData<ModelWeather[]> forecast = new MutableLiveData<>();

        weatherApi.getForecast(city.getLat(),city.getLon(),"c6e381d8c7ff98f0fee43775817cf6ad").enqueue(new Callback<ModelWeather[]>() {

            @Override
            public void onResponse(Call<ModelWeather[]> call, retrofit2.Response<ModelWeather[]> response) {
                forecast.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ModelWeather[]> call, Throwable t) {
                forecast.setValue(null);
            }
        });
        return forecast;

    }
}
