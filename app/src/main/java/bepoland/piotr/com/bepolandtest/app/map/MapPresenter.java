package bepoland.piotr.com.bepolandtest.app.map;

import android.location.Address;
import android.location.Geocoder;


import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by piotr on 20/05/17.
 */
public class MapPresenter implements MapContract.Presenter {

    private final MapContract.View view;
    private final Geocoder geocoder;
    private final WeatherApi weatherApi;
    private final DatabaseHelper databaseHelper;

    @Inject
    public MapPresenter(MapContract.View view, Geocoder
            geocoder, WeatherApi weatherApi, DatabaseHelper databaseHelper) {
        this.view = view;
        this.geocoder = geocoder;
        this.weatherApi=weatherApi;
        this.databaseHelper=databaseHelper;
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

        weatherApi.getWeather(position.latitude,position.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ModelWeather>>() {

                    @Override
                    public Observable<? extends ModelWeather> call(Throwable throwable) {

                        return null;
                    }
                }).subscribe(new Observer<ModelWeather>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.locationError();

            }

            @Override
            public void onNext(ModelWeather weather) {
                city.setWeather(weather);
                databaseHelper.saveCity(city);
                view.locationAdded();
                loadData();
            }
        });


    }

    @Override
    public void loadData() {
        view.publishData(databaseHelper.getCities());
    }
}
