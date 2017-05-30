package bepoland.piotr.com.bepolandtest;



import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by piotr on 12/05/17.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {


    private DatabaseHelper databaseHelper;

    @Before
    public void setup()
    {
        databaseHelper = new DatabaseHelper(new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(),"test_"));

    }

    @After
    public void teardown()
    {

        databaseHelper.dropDatabase(databaseHelper.getWritableDatabase());
    }

    @Test
    public void testSaveRetrieveAndDelete()
    {

        ModelCity modelCity = new ModelCity(new LatLng(0,0),"foo");
        ModelWeather modelWeather = new ModelWeather();
        modelCity.setWeather(modelWeather);
        databaseHelper.saveCity(modelCity);

        ModelCity[] result = databaseHelper.getCities();
        assertEquals(1,result.length);

        databaseHelper.removeCity(result[0]);
        result = databaseHelper.getCities();
        assertEquals(0,result.length);


    }



}
