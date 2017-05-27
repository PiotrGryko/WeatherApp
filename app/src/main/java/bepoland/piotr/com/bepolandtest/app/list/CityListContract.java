package bepoland.piotr.com.bepolandtest.app.list;

import android.widget.ImageView;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 12/05/17.
 */

public interface CityListContract {

    interface View {
        void publishData(ModelCity[] cities);
        void elementRemoved(ModelCity city);
        void showDetails(ModelCity city, ImageView image);
    }

    interface Presenter {
        void loadData();
        void removeElement(ModelCity city);
    }

}
