package bepoland.piotr.com.bepolandtest.data.module;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import javax.inject.Named;
import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.util.DAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 10/05/17.
 * <p>
 * provide dependencies for valley
 */

@Module
public class DaoModule {


    @Provides
    @Singleton
    Cache provideCache(Application application) {
        Cache cache = new DiskBasedCache(application.getCacheDir(), 1024 * 1024); // 1MB cap
        return cache;
    }

    @Provides
    @Singleton
    RequestQueue provideRequestQueue(Cache cache) {
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        return mRequestQueue;
    }


    @Provides
    @Named("baseUrl")
    String provideBaseUrl() {
        return "http://api.openweathermap.org/data/2.5/";
    }

    @Provides
    @Singleton
    DAO provideDAO(RequestQueue requestQueue, @Named("baseUrl") String baseUrl) {
        return new DAO(requestQueue, baseUrl);
    }


}
