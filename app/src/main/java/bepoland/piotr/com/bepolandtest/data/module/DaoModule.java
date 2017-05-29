package bepoland.piotr.com.bepolandtest.data.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import bepoland.piotr.com.bepolandtest.util.WeatherResponseDeserializer;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piotr on 10/05/17.
 * <p>
 * provide dependencies for valley
 */

@Module
public class DaoModule {




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

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter("appid","c6e381d8c7ff98f0fee43775817cf6ad").build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        return retrofit.create(WeatherApi.class);
    }


}
