package bepoland.piotr.com.bepolandtest.app.map;

import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 20/05/17.
 */
public interface MapContract {

    public interface Presenter{
        void addLocation(LatLng position);
        void loadData();
    }
    public interface View
    {
        void publishData(ModelCity[] data);
    }

}
