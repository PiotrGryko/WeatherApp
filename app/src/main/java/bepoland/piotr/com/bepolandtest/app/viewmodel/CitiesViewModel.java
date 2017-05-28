package bepoland.piotr.com.bepolandtest.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;

/**
 * Created by piotr on 27/05/17.
 */

public class CitiesViewModel extends ViewModel {

    private LiveData<ModelCity[]> cities;
    private final CitiesRepository citiesRepository;
    private boolean hasChanged=true;
    @Inject
    public CitiesViewModel(CitiesRepository citiesRepository) {

        this.citiesRepository = citiesRepository;
    }

    public LiveData<ModelCity> addCity(LatLng position) {
        hasChanged=true;
        return citiesRepository.addLocation(position);
    }

    public LiveData<ModelCity> removeCity(ModelCity city) {
        hasChanged=true;
        return citiesRepository.removeElement(city);
    }

    public LiveData<ModelCity[]> getCities() {

        if (cities == null || hasChanged) {
            cities = citiesRepository.loadCities();
            hasChanged=false;
        }

        return cities;
    }

    public LiveData<ModelWeather[]> loadForecast(ModelCity city) {

        return citiesRepository.loadForecast(city);
    }
}
