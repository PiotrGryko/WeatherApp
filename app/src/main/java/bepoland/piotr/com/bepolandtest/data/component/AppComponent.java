package bepoland.piotr.com.bepolandtest.data.component;

import android.location.Geocoder;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import bepoland.piotr.com.bepolandtest.data.module.DaoModule;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import dagger.Component;

/**
 * Created by piotr on 10/05/17.
 */

@Singleton
@Component(modules = {AppModule.class,DaoModule.class})
public interface AppComponent {
    //tell dagger that MainActivity has access to singletons
    //dont use base classes
    //void inject(MainActivity activity);
    //DAO needs to be exposed for CityListComponent
    WeatherApi weatherApi();
    Geocoder geocoder();
    DatabaseHelper databaseHelper();

}
