
package bepoland.piotr.com.bepolandtest;


import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomDatabase;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DbTest {
    protected CitiesRoomDatabase db;


    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), CitiesRoomDatabase.class).build();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void testSaveAndRetrieve()
    {
        final ModelCity modelCity = new ModelCity(0,0,"foo");
        db.citiesDao().saveCity(modelCity);

        ModelCity[] result = db.citiesDao().getCities();
        assertEquals(result.length,1);
        assertEquals(modelCity.getName(),result[0].getName());

        db.citiesDao().deleteCity(result[0]);
        result = db.citiesDao().getCities();
        assertEquals(result.length,0);


    }
}
