package bepoland.piotr.com.bepolandtest.app.list;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 12/05/17.
 */

@Module
public class CityListModule {
    private final CityListContract.View mView;
    public CityListModule(CityListContract.View mView)
    {
        this.mView=mView;
    }

    @Provides
    CityListContract.View provideListContractView()
    {
        return mView;
    }
}
