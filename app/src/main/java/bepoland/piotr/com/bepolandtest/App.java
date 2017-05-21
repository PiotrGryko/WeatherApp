package bepoland.piotr.com.bepolandtest;

import android.app.Application;
import android.location.Geocoder;



import java.util.Locale;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 10/05/17.
 */
public class App extends Application {

    private Geocoder geocoder;
    private DatabaseHelper databaseHelper;
    private DAO dao;

    public void onCreate() {
        super.onCreate(); this.geocoder = new Geocoder(this, Locale.getDefault());
        this.databaseHelper = new DatabaseHelper(this);
        this.dao = new DAO("http://api.openweathermap.org/data/2.5/");
    }

    public DatabaseHelper provideDatabaseHelper() {
        return databaseHelper;
    }

    public Geocoder provideGeocoder() {
        return geocoder;
    }

    public DAO provideDao() {
        return dao;
    }
}
