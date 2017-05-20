package bepoland.piotr.com.bepolandtest;

import android.app.Application;

import bepoland.piotr.com.bepolandtest.data.component.DaggerDaoComponent;
import bepoland.piotr.com.bepolandtest.data.component.DaoComponent;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import bepoland.piotr.com.bepolandtest.data.module.DaoModule;

/**
 * Created by piotr on 10/05/17.
 */

public class App extends Application {

    DaoComponent daoComponent;
    public void onCreate()
    {
        super.onCreate();
        daoComponent = DaggerDaoComponent.builder().appModule(new AppModule(this)).daoModule(new DaoModule()).build();
    }


    public DaoComponent getDaoComponent()
    {
        return daoComponent;
    }

}
