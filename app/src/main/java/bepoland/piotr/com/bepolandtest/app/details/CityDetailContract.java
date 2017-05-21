package bepoland.piotr.com.bepolandtest.app.details;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;

/**
 * Created by piotr on 17/05/17.
 */

public interface CityDetailContract {

    public interface Presenter {
        public void loadForecast(ModelCity city);
    }

    public interface View {
        public void publishData(ModelWeather[] weather);
        public void forecastLoaded();
        public void forecastError();
    }

}
