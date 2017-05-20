package bepoland.piotr.com.bepolandtest.app.map;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piotr on 20/05/17.
 */
@Module
public class MapModule {

    private final MapContract.View view;

    public MapModule(MapContract.View view) {
        this.view = view;
    }

    @Provides
    public MapContract.View provideMapContractView() {
        return this.view;
    }
}
