package bepoland.piotr.com.bepolandtest.di;

import android.location.Geocoder;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.App;
import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.util.WeatherApi;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by piotr on 10/05/17.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, RetrofitModule.class, MainActivityModule.class})
public interface AppComponent {

    //tell dagger that MainActivity has access to singletons
    //dont use base classes
    //void inject(MainActivity activity);
    //DAO needs to be exposed for CityListComponent
    WeatherApi weatherApi();

    Geocoder geocoder();

    CitiesRoomHelper citiesRoomHelper();

    void inject(App app);

}
