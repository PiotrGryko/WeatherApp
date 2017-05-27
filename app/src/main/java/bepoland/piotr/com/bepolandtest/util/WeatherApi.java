package bepoland.piotr.com.bepolandtest.util;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piotr on 24/05/17.
 */

public interface WeatherApi {


    @GET("weather")
    Call<ModelWeather> getWeather(@Query("lat") double lattitude, @Query("lon") double longitude, @Query("appid") String key);

    @GET("forecast")
    Call<ModelWeather[]> getForecast(@Query("lat") double lattitude, @Query("lon") double longitude, @Query("appid") String key);
}
