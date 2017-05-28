package bepoland.piotr.com.bepolandtest.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.location.Geocoder;

import java.util.Locale;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomDatabase;
import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesRepository;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CustomViewModelFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 10/05/17.
 * <p>
 * <p>
 * Module to provide application context. Required for caching
 */
@Module(subcomponents = ViewModelSubcomponent.class)
public class AppModule {

    Application mApplication;
    Geocoder geocoder;

    public AppModule(Application mApplication) {

        this.mApplication = mApplication;
        this.geocoder = new Geocoder(mApplication, Locale.getDefault());
    }

    @Provides
    @Singleton
    CitiesRoomHelper provideDatabaseRoom() {

        CitiesRoomDatabase citiesRoomDatabase = Room.databaseBuilder(mApplication,
                CitiesRoomDatabase.class, "database-name").build();
        return new CitiesRoomHelper(citiesRoomDatabase);
    }

    /*
    @Provides
    @Singleton
    CitiesRepository provideCitiesRepository(CitiesRoomHelper citiesRoomHelper) {

        return new CitiesRepository(citiesRoomHelper);
    }
    */
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

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ViewModelSubcomponent.Builder
                                                              viewModelSubComponent) {

        return new CustomViewModelFactory(viewModelSubComponent.build());
    }
}