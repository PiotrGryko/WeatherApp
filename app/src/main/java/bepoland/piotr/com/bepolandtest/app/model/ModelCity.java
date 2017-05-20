package bepoland.piotr.com.bepolandtest.app.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by piotr on 20/05/17.
 */
public class ModelCity implements Serializable {

    private static final long serialVersionUID = 1L;
    private LatLng position;
    private String name;

    public ModelCity(LatLng position, String name) {
        this.position = position;
        this.name = name;
    }

    public String getName() {return name;}

    public String getPosition() {
        return position.toString();
    }

    public LatLng getLatLng() {
        return position;
    }
}
