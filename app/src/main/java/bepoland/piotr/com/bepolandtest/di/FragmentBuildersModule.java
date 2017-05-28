package bepoland.piotr.com.bepolandtest.di;

import bepoland.piotr.com.bepolandtest.app.details.CityDetailFragment;
import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;
import bepoland.piotr.com.bepolandtest.app.map.MapFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by piotr on 28/05/17.
 */

@Module
public abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract CityListFragment contributeCityListFragment();

    @ContributesAndroidInjector
    abstract MapFragment contributeMapFragment();

    @ContributesAndroidInjector
    abstract CityDetailFragment contributeDetailFragment();
}
