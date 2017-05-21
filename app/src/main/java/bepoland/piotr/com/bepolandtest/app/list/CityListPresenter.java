package bepoland.piotr.com.bepolandtest.app.list;


import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 12/05/17.
 */
public class CityListPresenter implements CityListContract.Presenter {

    private String TAG = CityListPresenter.class.getName();
    private final DatabaseHelper databaseHelper;
    private final CityListContract.View view;

    public CityListPresenter(DatabaseHelper databaseHelper, CityListContract.View view) {
        this.databaseHelper=databaseHelper;
        this.view = view;
    }

    @Override
    public void loadData() {
        view.publishData(databaseHelper.getCities());
    }

    @Override
    public void removeElement(ModelCity city) {
        this.databaseHelper.removeCity(city);
        //loadData();
    }
}
