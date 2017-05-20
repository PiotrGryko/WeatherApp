package bepoland.piotr.com.bepolandtest.util;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 10/05/17.
 */

public class DAO {


    RequestQueue requestQueue;
    String baseUrl;

    public DAO(RequestQueue requestQueue, String baseUrl) {
        this.requestQueue = requestQueue;
        this.baseUrl = baseUrl;

    }


}
