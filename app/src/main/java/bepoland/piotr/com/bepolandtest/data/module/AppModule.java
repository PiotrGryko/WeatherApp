package bepoland.piotr.com.bepolandtest.data.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.location.Geocoder;

import java.util.Locale;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomDatabase;
import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
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
    CitiesRoomHelper citiesRoomHelper;
    //CitiesRoomDatabase citiesRoomDatabase;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
        this.geocoder = new Geocoder(mApplication, Locale.getDefault());
        CitiesRoomDatabase citiesRoomDatabase= Room.databaseBuilder(mApplication,CitiesRoomDatabase.class,"database-name").build();
        this.citiesRoomHelper= new CitiesRoomHelper(citiesRoomDatabase);

    }

    @Provides
    @Singleton
    CitiesRoomHelper provideDatabaseRoom() {
        return citiesRoomHelper;
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