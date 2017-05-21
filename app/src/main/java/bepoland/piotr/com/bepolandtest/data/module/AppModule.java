package bepoland.piotr.com.bepolandtest.data.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.preference.PreferenceManager;

import java.util.Locale;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
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
    Geocoder geocoder;
    DatabaseHelper databaseHelper;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
        this.geocoder = new Geocoder(mApplication, Locale.getDefault());
        this.databaseHelper = new DatabaseHelper(mApplication);

    }
    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return databaseHelper;
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

}