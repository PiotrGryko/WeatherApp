package bepoland.piotr.com.bepolandtest.data.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import bepoland.piotr.com.bepolandtest.util.WeatherResponseDeserializer;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piotr on 10/05/17.
 * <p>
 * provide dependencies for valley
 */

@Module
public class RetrofitModule {




    @Provides
    @Named("baseUrl")
    String provideBaseUrl() {
        return "http://api.openweathermap.org/data/2.5/";
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(@Named("baseUrl") String baseUrl) {

        Gson gson = new GsonBuilder().registerTypeAdapter(ModelWeather.class,new WeatherResponseDeserializer.WeatherObjectDeserializer())
                .registerTypeAdapter(ModelWeather[].class, new WeatherResponseDeserializer.WeatherArrayDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit.create(WeatherApi.class);
    }


}
