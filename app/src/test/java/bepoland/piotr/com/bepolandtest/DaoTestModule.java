package bepoland.piotr.com.bepolandtest;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.NoCache;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.data.module.DaoModule;
import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 10/05/17.
 *
 * provide dependencies for valley
 */

@Module
public class DaoTestModule extends DaoModule{


    private String mockResponse = "{\"coord\":{\"lon\":0,\"lat\":0},\"weather\":[{\"id\":803," +
            "\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}]," +
            "\"base\":\"stations\",\"main\":{\"temp\":27.36,\"pressure\":1025.37," +
            "\"humidity\":100,\"temp_min\":27.36,\"temp_max\":27.36,\"sea_level\":1025.51," +
            "\"grnd_level\":1025.37},\"wind\":{\"speed\":6.16,\"deg\":195.002}," +
            "\"clouds\":{\"all\":64},\"dt\":1495404628,\"sys\":{\"message\":0.0021," +
            "\"sunrise\":1495345986,\"sunset\":1495389615},\"id\":6295630,\"name\":\"Earth\"," +
            "\"cod\":200}";

    @Provides
    @Singleton
    Cache provideTestCache()
    {
        return new NoCache();
    }

    @Provides
    @Singleton
    RequestQueue provideTestRequestQueue(Cache cache) {
        Network network = new BasicNetwork(new HttpStack() {
            @Override
            public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {
                HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "OK");
                response.setEntity(new StringEntity(mockResponse));
                return response;
            }
        });
        ResponseDelivery responseDelivery = new ExecutorDelivery(Executors.newSingleThreadExecutor());
        RequestQueue queue = new RequestQueue(cache, network, 1,responseDelivery);
        queue.start();
        return queue;
    }
    @Provides
    @Named("baseUrl")
    String provideBaseUrl() {
        return "https://www.example.com";
    }

    @Provides
    @Singleton
    DAO provideTestDAO(RequestQueue requestQueue, @Named("baseUrl") String baseUrl) {
        return new DAO(requestQueue, baseUrl);
    }




}
