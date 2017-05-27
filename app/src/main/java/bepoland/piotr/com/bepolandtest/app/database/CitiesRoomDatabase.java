package bepoland.piotr.com.bepolandtest.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import retrofit2.Converter;

/**
 * Created by piotr on 26/05/17.
 */

@Database(entities = {ModelCity.class},version = 1)
@TypeConverters({DateConverter.class})
public abstract class CitiesRoomDatabase extends RoomDatabase {
    public abstract CitiesDao citiesDao();
}
