package bepoland.piotr.com.bepolandtest.app.list;

import bepoland.piotr.com.bepolandtest.data.component.DaoComponent;
import bepoland.piotr.com.bepolandtest.util.CustomScope;
import dagger.Component;

/**
 * Created by piotr on 12/05/17.
 */

@CustomScope
@Component(dependencies = DaoComponent.class,modules = CityListModule.class)
public interface CityListComponent {
    void inject(CityListFragment fragment);
}
