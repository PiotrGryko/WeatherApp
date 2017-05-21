package bepoland.piotr.com.bepolandtest.app.list;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.util.CustomScope;
import dagger.Component;

/**
 * Created by piotr on 12/05/17.
 */

@CustomScope
@Component(dependencies = AppComponent.class,modules = CityListModule.class)
public interface CityListComponent {
    void inject(CityListFragment fragment);
}
