package bepoland.piotr.com.bepolandtest.app.details;


import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

        weatherApi.getForecast(city.getLat(),city.getLon())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ModelWeather[]>>() {

                    @Override
                    public Observable<? extends ModelWeather[]> call(Throwable throwable) {

                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<ModelWeather[]>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                contractView.forecastError();

            }

            @Override
            public void onNext(ModelWeather[] modelWeathers) {
                contractView.forecastLoaded();
                contractView.publishData(modelWeathers);
            }
        });





    }
}
