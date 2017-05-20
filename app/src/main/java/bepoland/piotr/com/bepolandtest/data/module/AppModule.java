package bepoland.piotr.com.bepolandtest.data.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.preference.PreferenceManager;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 10/05/17.
 * <p>
 * <p>
 * Module to provide application context. Required for caching
 */
@Module
public class AppModule {

    Application mApplication;
    SharedPreferences preferences;
    Geocoder geocoder;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(mApplication);
        this.geocoder = new Geocoder(mApplication, Locale.getDefault());

    }

    @Provides
    @Singleton
    Geocoder provideGeocoder() {
        return geocoder;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return preferences;
    }
}