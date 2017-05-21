package bepoland.piotr.com.bepolandtest.app.details;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.util.CustomScope;
import dagger.Component;

/**
 * Created by piotr on 17/05/17.
 */

@CustomScope
@Component(dependencies = AppComponent.class, modules = CityDetailModule.class)
public interface CityDetailComponent {
    void inject(CityDetailFragment fragment);
}
