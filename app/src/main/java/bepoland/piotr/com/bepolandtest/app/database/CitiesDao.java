package bepoland.piotr.com.bepolandtest.app.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 26/05/17.
 */

@Dao
public interface CitiesDao {

    @Insert
    public long saveCity(ModelCity city);

    @Delete
    public int deleteCity(ModelCity city);

    @Query("SELECT * FROM city")
    public ModelCity[] getCities();
}
