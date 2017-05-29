package bepoland.piotr.com.bepolandtest.util;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by piotr on 24/05/17.
 */

public interface WeatherApi {


    @GET("weather")
    Observable<ModelWeather> getWeather(@Query("lat") double lattitude, @Query("lon") double longitude);

    @GET("forecast")
    Observable<ModelWeather[]> getForecast(@Query("lat") double lattitude, @Query("lon") double longitude);
}
