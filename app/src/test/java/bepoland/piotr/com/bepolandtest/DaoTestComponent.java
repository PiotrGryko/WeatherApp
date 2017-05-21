package bepoland.piotr.com.bepolandtest;

import javax.inject.Singleton;

import bepoland.piotr.com.bepolandtest.data.component.AppComponent;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import dagger.Component;

/**
 * Created by piotr on 11/05/17.
 */

@Singleton
@Component(modules = {AppModule.class,DaoTestModule.class})
public interface DaoTestComponent extends AppComponent{
    void inject(DaoUnitTest test);
    void inject(ListFragmentPresenterTest test);
    void inject(DetailFragmentPresenterTest test);
}