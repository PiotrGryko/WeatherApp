package bepoland.piotr.com.bepolandtest.di;

import bepoland.piotr.com.bepolandtest.MainActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by piotr on 28/05/17.
 */

@Subcomponent
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}