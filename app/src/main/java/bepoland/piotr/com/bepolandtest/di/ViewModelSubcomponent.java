package bepoland.piotr.com.bepolandtest.di;

import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesViewModel;
import dagger.Subcomponent;

/**
 * Created by piotr on 27/05/17.
 */

@Subcomponent
public interface ViewModelSubcomponent {

    @Subcomponent.Builder
    interface Builder {

        ViewModelSubcomponent build();
    }

    CitiesViewModel citiesViewModel();
}
