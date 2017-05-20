package bepoland.piotr.com.bepolandtest.app.details;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 17/05/17.
 */

public interface CityDetailContract {

    public interface Presenter {
        public void loadData(ModelCity city);
    }

    public interface View {
        public void publishData(ModelCity city);
    }

}
