package bepoland.piotr.com.bepolandtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.details.CityDetailContract;
import bepoland.piotr.com.bepolandtest.app.details.CityDetailPresenter;
import bepoland.piotr.com.bepolandtest.util.DAO;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by piotr on 20/05/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailFragmentPresenterTest {

    private CityDetailPresenter presenter;
    @Inject
    DAO dao;

    @Mock
    CityDetailContract.View view;
    private final CountDownLatch signal = new CountDownLatch(1);


    @Before
    public void setUp()
    {
        DaggerDaoTestComponent.builder().daoTestModule(new DaoTestModule()).build().inject(this);
        MockitoAnnotations.initMocks(this);
        presenter = new CityDetailPresenter(dao,view);
    }

    @Test
    public void presenterShouldLoadData()
    {
        assertNotNull(presenter);

    }
}
