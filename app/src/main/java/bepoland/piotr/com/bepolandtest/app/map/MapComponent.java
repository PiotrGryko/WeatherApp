package bepoland.piotr.com.bepolandtest.app.map;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.util.CustomScope;
import dagger.Component;

/**
 * Created by piotr on 20/05/17.
 */
@CustomScope
@Component(dependencies = AppComponent.class, modules = MapModule.class)
public interface MapComponent {

    void inject(MapFragment fragment);
}
