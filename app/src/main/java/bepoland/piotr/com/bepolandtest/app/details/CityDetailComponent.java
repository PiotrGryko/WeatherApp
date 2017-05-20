package bepoland.piotr.com.bepolandtest.app.details;

import bepoland.piotr.com.bepolandtest.data.component.DaoComponent;
import bepoland.piotr.com.bepolandtest.util.CustomScope;
import dagger.Component;

/**
 * Created by piotr on 17/05/17.
 */

@CustomScope
@Component(dependencies = DaoComponent.class, modules = CityDetailModule.class)
public interface CityDetailComponent {
    void inject(CityDetailFragment fragment);
}
