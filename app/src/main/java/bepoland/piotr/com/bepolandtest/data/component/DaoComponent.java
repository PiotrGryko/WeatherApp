package bepoland.piotr.com.bepolandtest.data.component;

import android.content.SharedPreferences;
import android.location.Geocoder;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import bepoland.piotr.com.bepolandtest.data.module.DaoModule;
import bepoland.piotr.com.bepolandtest.util.DAO;
import dagger.Component;

/**
 * Created by piotr on 10/05/17.
 */

@Singleton
@Component(modules = {AppModule.class,DaoModule.class})
public interface DaoComponent {
    //tell dagger that MainActivity has access to singletons
    //dont use base classes
    //void inject(MainActivity activity);
    //DAO needs to be exposed for CityListComponent
    DAO dao();
    SharedPreferences sharedPreferences();
    Geocoder geocoder();

}
