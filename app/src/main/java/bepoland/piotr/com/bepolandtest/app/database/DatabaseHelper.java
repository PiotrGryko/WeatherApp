package bepoland.piotr.com.bepolandtest.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 23/02/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CitiesDatabase.db";

    private static final String SQL_CREATE_CITIES =
            "CREATE TABLE " + CitiesDatabase.City.TABLE_NAME + " (" +
                    CitiesDatabase.City._ID + " INTEGER PRIMARY KEY," +
                    CitiesDatabase.City.COLUMN_NAME + " TEXT," +
                    CitiesDatabase.City.COLUMN_LAT + " REAL," +
                    CitiesDatabase.City.COLUMN_LON + " REAL)";

    private static final String SQL_DELETE_CITIES =
            "DROP TABLE IF EXISTS " + CitiesDatabase.City.TABLE_NAME;


    private static final String SQL_CREATE_WEATHERS =
            "CREATE TABLE " + CitiesDatabase.Weather.TABLE_NAME + " (" +
                    CitiesDatabase.Weather._ID + " INTEGER PRIMARY KEY," +
                    CitiesDatabase.Weather.COLUMN_DATE + " INTEGER," +
                    CitiesDatabase.Weather.COLUMN_DESCRIPTION + " TEXT," +
                    CitiesDatabase.Weather.COLUMN_HUMIDITY + " TEXT," +
                    CitiesDatabase.Weather.COLUMN_IMAGE_URL + " TEXT," +
                    CitiesDatabase.Weather.COLUMN_PRESSURE + " REAL," +
                    CitiesDatabase.Weather.COLUMN_SEA_LEVEL + " REAL," +
                    CitiesDatabase.Weather.COLUMN_SUNRISE + " INTEGER," +
                    CitiesDatabase.Weather.COLUMN_SUNSET + " INTEGER," +
                    CitiesDatabase.Weather.COLUMN_TEMP + " REAL," +
                    CitiesDatabase.Weather.COLUMN_TEMP_MIN + " REAL," +
                    CitiesDatabase.Weather.COLUMN_TEMP_MAX + " REAL," +
                    CitiesDatabase.Weather.COLUMN_WIND_SPEED + " REAL,"+
                    CitiesDatabase.Weather.CITY_ID+ " INTEGER, FOREIGN KEY ("+CitiesDatabase.Weather.CITY_ID+") REFERENCES city(_id));";


    private static final String SQL_DELETE_WEATHERS =
            "DROP TABLE IF EXISTS " + CitiesDatabase.Weather.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_CITIES);
        db.execSQL(SQL_CREATE_WEATHERS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CITIES);
        db.execSQL(SQL_DELETE_WEATHERS);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void saveCity(ModelCity city) {
        SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(CitiesDatabase.City.COLUMN_NAME, city.getName());
        values.put(CitiesDatabase.City.COLUMN_LAT, city.getLat());
        values.put(CitiesDatabase.City.COLUMN_LON,city.getLon());

        long cityId= db.insert(CitiesDatabase.City.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(CitiesDatabase.Weather.CITY_ID, cityId);
        values.put(CitiesDatabase.Weather.COLUMN_DATE, city.getWeather().getRawDate());
        values.put(CitiesDatabase.Weather.COLUMN_DESCRIPTION,city.getWeather().getDescirption());
        values.put(CitiesDatabase.Weather.COLUMN_HUMIDITY,city.getWeather().getHumidity());
        values.put(CitiesDatabase.Weather.COLUMN_IMAGE_URL,city.getWeather().getRawImageUrl());
        values.put(CitiesDatabase.Weather.COLUMN_PRESSURE,city.getWeather().getPressure());
        values.put(CitiesDatabase.Weather.COLUMN_SEA_LEVEL,city.getWeather().getSeaLevel());
        values.put(CitiesDatabase.Weather.COLUMN_SUNRISE,city.getWeather().getRawSunriseDate());
        values.put(CitiesDatabase.Weather.COLUMN_SUNSET,city.getWeather().getRawSunsetDate());
        values.put(CitiesDatabase.Weather.COLUMN_TEMP,city.getWeather().getTemperature());
        values.put(CitiesDatabase.Weather.COLUMN_TEMP_MAX,city.getWeather().getMaxTemperature());
        values.put(CitiesDatabase.Weather.COLUMN_TEMP_MIN,city.getWeather().getMinTemperature());
        values.put(CitiesDatabase.Weather.COLUMN_WIND_SPEED,city.getWeather().getWindSpeed());

        long weatherId = db.insert(CitiesDatabase.Weather.TABLE_NAME, null, values);
    }

    public void removeCity(ModelCity city)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CitiesDatabase.Weather.TABLE_NAME,CitiesDatabase.Weather._ID + "=" + city.getWeather().getId(), null);
        db.delete(CitiesDatabase.City.TABLE_NAME,CitiesDatabase.City._ID + "=" + city.getWeather().getId(), null);

    }



    public ModelCity[] getCities() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                CitiesDatabase.City.TABLE_NAME+"."+CitiesDatabase.City._ID,
                CitiesDatabase.City.COLUMN_NAME,
                CitiesDatabase.City.COLUMN_LAT,
                CitiesDatabase.City.COLUMN_LON,

                CitiesDatabase.Weather.TABLE_NAME+"."+CitiesDatabase.Weather._ID,
                CitiesDatabase.Weather.COLUMN_DATE,
                CitiesDatabase.Weather.COLUMN_DESCRIPTION,
                CitiesDatabase.Weather.COLUMN_HUMIDITY,
                CitiesDatabase.Weather.COLUMN_IMAGE_URL,
                CitiesDatabase.Weather.COLUMN_PRESSURE,
                CitiesDatabase.Weather.COLUMN_SEA_LEVEL,
                CitiesDatabase.Weather.COLUMN_SUNRISE,
                CitiesDatabase.Weather.COLUMN_SUNSET,
                CitiesDatabase.Weather.COLUMN_TEMP_MAX,
                CitiesDatabase.Weather.COLUMN_TEMP,
                CitiesDatabase.Weather.COLUMN_TEMP_MIN,
                CitiesDatabase.Weather.COLUMN_WIND_SPEED
        };


        String tables = CitiesDatabase.City.TABLE_NAME+ " LEFT OUTER JOIN "
                +CitiesDatabase.Weather.TABLE_NAME +" ON "
                +CitiesDatabase.City.TABLE_NAME+"._id="
                +CitiesDatabase.Weather.TABLE_NAME+".city_id";

        Cursor cursor = db.query(
                tables,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                // The sort order
        );
        ModelCity[] items = new ModelCity[cursor.getCount()];
        int index=0;
        while (cursor.moveToNext()) {
            items[index++]=ModelCity.valueOf(cursor);
        }
        cursor.close();


        return items;
    }





}
