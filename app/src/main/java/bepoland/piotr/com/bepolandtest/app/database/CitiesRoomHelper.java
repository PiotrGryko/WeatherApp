package bepoland.piotr.com.bepolandtest.app.database;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 26/05/17.
 */

public class CitiesRoomHelper {

    private final CitiesRoomDatabase citiesRoomDatabase;
    private final Handler handler;

    public CitiesRoomHelper(CitiesRoomDatabase citiesRoomDatabase) {

        this.citiesRoomDatabase = citiesRoomDatabase;
        this.handler = new Handler();
    }

    public interface OnOperationFinished<T> {

        public void onFinished(T result);
    }

    public void saveData(final ModelCity modelCity, final OnOperationFinished<Long> listener) {

        new Thread() {

            public void run() {

                final long id = citiesRoomDatabase.citiesDao().saveCity(modelCity);
                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        listener.onFinished(id);
                    }
                });
            }
        }.start();
    }

    public void removeData(final ModelCity modelCity, final OnOperationFinished<Integer> listener) {

        new Thread() {

            public void run() {

                final int count = citiesRoomDatabase.citiesDao().deleteCity(modelCity);
                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        listener.onFinished(count);
                    }
                });
            }
        }.start();
    }

    public void loadData(final OnOperationFinished<ModelCity[]> listener) {

        new Thread() {

            public void run() {

                final ModelCity[] result = citiesRoomDatabase.citiesDao().getCities();
                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        listener.onFinished(result);
                    }
                });
            }
        }.start();
    }
}
