package bepoland.piotr.com.bepolandtest.app.list;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Arrays;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.util.DAO;

/**
 * Created by piotr on 12/05/17.
 */

public class CityListPresenter implements CityListContract.Presenter {

    private String TAG = CityListPresenter.class.getName();
    DAO dao;
    CityListContract.View view;

    @Inject
    public CityListPresenter(DAO dao, CityListContract.View view) {
        this.dao = dao;
        this.view = view;
    }


    @Override
    public void loadData() {
    }
}
