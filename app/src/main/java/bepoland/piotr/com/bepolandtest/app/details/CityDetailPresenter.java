package bepoland.piotr.com.bepolandtest.app.details;


import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by piotr on 17/05/17.
 */

public class CityDetailPresenter implements CityDetailContract.Presenter {

    private final WeatherApi weatherApi;
    private final CityDetailContract.View contractView;

    @Inject
    public CityDetailPresenter(WeatherApi weatherApi, CityDetailContract.View view) {
        this.weatherApi=weatherApi;
        this.contractView = view;
    }

    @Override
    public void loadForecast(ModelCity city) {

        weatherApi.getForecast(city.getLat(),city.getLon(),"c6e381d8c7ff98f0fee43775817cf6ad").enqueue(new Callback<ModelWeather[]>() {

            @Override
            public void onResponse(Call<ModelWeather[]> call, retrofit2.Response<ModelWeather[]> response) {
                contractView.forecastLoaded();
                contractView.publishData(response.body());
            }

            @Override
            public void onFailure(Call<ModelWeather[]> call, Throwable t) {
                contractView.forecastError();
            }
        });


    }
}
