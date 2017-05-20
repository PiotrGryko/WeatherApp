package bepoland.piotr.com.bepolandtest.app.details;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 17/05/17.
 */

public class CityDetailPresenter implements CityDetailContract.Presenter {

    DAO dao;
    CityDetailContract.View contractView;

    @Inject
    public CityDetailPresenter(DAO dao, CityDetailContract.View view) {
        this.dao = dao;
        this.contractView = view;
    }

    @Override
    public void loadData(ModelCity user) {
        /*
        dao.loadUserDetails(user, new Response.Listener<ModelUser>() {
            @Override
            public void onResponse(ModelUser response) {
                contractView.publishData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    */
    }
}
