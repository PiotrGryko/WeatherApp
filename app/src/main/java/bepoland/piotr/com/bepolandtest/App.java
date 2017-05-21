package bepoland.piotr.com.bepolandtest;

import android.app.Application;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.data.component.DaggerAppComponent;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import bepoland.piotr.com.bepolandtest.data.module.DaoModule;

/**
 * Created by piotr on 10/05/17.
 */

public class App extends Application {

    AppComponent appComponent;
    public void onCreate()
    {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).daoModule(new DaoModule()).build();
    }


    public AppComponent getAppComponent()
    {
        return appComponent;
    }

}
