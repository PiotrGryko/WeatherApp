package bepoland.piotr.com.bepolandtest.app.details;


import com.google.android.gms.maps.model.LatLng;


import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.DAO;
import bepoland.piotr.com.bepolandtest.util.HttpRequestTask;

/**
 * Created by piotr on 17/05/17.
 */

public class CityDetailPresenter implements CityDetailContract.Presenter {

    private final DAO dao;
    private final CityDetailContract.View contractView;


    public CityDetailPresenter(DAO dao, CityDetailContract.View view) {
        this.dao = dao;
        this.contractView = view;
    }

    @Override
    public void loadForecast(ModelCity city) {

        dao.loadForecast(new LatLng(city.getLat(), city.getLon()), new HttpRequestTask.OnRequestListener<ModelWeather[]>() {

            @Override
            public void onSuccess(ModelWeather[] data) {
                contractView.forecastLoaded();
                contractView.publishData(data);
            }

            @Override
            public void onError(Exception error) {
                contractView.forecastError();

            }
        });


    }
}
