package bepoland.piotr.com.bepolandtest;

import android.app.Application;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.data.component.DaggerAppComponent;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import bepoland.piotr.com.bepolandtest.data.module.RetrofitModule;

/**
 * Created by piotr on 10/05/17.
 */

public class App extends Application {

    AppComponent appComponent;
    public void onCreate()
    {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).retrofitModule(new RetrofitModule()).build();
    }


    public AppComponent getAppComponent()
    {
        return appComponent;
    }

}
