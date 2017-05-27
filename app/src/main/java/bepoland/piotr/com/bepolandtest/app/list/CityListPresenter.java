package bepoland.piotr.com.bepolandtest.app.list;

import android.util.Log;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.database.CitiesRoomHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 12/05/17.
 */
public class CityListPresenter implements CityListContract.Presenter {

    private String TAG = CityListPresenter.class.getName();
    CitiesRoomHelper citiesRoomHelper;
    CityListContract.View view;

    @Inject
    public CityListPresenter(CitiesRoomHelper citiesRoomHelper,
                             CityListContract.View view) {
        this.citiesRoomHelper = citiesRoomHelper;
        this.view = view;
    }

    @Override
    public void loadData() {

        citiesRoomHelper.loadData(new CitiesRoomHelper.OnOperationFinished<ModelCity[]>() {

            @Override
            public void onFinished(ModelCity[] result) {

                view.publishData(result);
            }
        });
    }

    @Override
    public void removeElement(final ModelCity city) {

        this.citiesRoomHelper.removeData(city, new CitiesRoomHelper.OnOperationFinished<Integer>() {

            @Override
            public void onFinished(Integer result) {
                    view.elementRemoved(city);
            }
        });
    }
}
