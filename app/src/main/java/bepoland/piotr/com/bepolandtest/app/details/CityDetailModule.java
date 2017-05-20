package bepoland.piotr.com.bepolandtest.app.details;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 17/05/17.
 */

@Module
public class CityDetailModule {
    private final CityDetailContract.View view;

    public CityDetailModule(CityDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public CityDetailContract.View provideCityDetailContractView() {
        return this.view;
    }
}
