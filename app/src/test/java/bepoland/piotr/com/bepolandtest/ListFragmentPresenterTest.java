package bepoland.piotr.com.bepolandtest;

import android.app.Application;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import bepoland.piotr.com.bepolandtest.app.database.DatabaseHelper;
import bepoland.piotr.com.bepolandtest.app.list.CityListContract;
import bepoland.piotr.com.bepolandtest.app.list.CityListPresenter;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by piotr on 19/05/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ListFragmentPresenterTest {

    private CityListPresenter presenter;
    @Mock
    CityListContract.View view;
    private final CountDownLatch signal = new CountDownLatch(1);


    @Before
    public void setUp()
    {
        Application application = RuntimeEnvironment.application;
        DaggerDaoTestComponent.builder().appModule(new AppModule(application)).daoTestModule(new DaoTestModule()).build().inject(this);
        MockitoAnnotations.initMocks(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(application);
        presenter = new CityListPresenter(databaseHelper,view);
    }

    @Test
    public void presenterShouldLoadData()
    {
        assertNotNull(presenter);
        presenter.loadData();
        try {
            System.out.println("waiting 30secs..");
            signal.await(30, TimeUnit.SECONDS); // wait for callback
            signal.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mockito.verify(view).publishData(Mockito.any(ModelCity[].class));
    }
}
