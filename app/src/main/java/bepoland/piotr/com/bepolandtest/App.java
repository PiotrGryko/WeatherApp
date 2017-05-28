package bepoland.piotr.com.bepolandtest;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.di.AppComponent;
import bepoland.piotr.com.bepolandtest.di.AppModule;
import bepoland.piotr.com.bepolandtest.di.DaggerAppComponent;
import bepoland.piotr.com.bepolandtest.di.RetrofitModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by piotr on 10/05/17.
 */

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    AppComponent appComponent;

    public void onCreate()
    {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).retrofitModule(new RetrofitModule()).build();
        appComponent.inject(this);
    }


    public AppComponent getAppComponent()
    {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {

        return dispatchingAndroidInjector;
    }
}
