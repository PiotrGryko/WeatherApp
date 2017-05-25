package bepoland.piotr.com.bepolandtest;

import android.app.Application;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.data.module.AppModule;

import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DaoUnitTest {

    @Inject
    DAO dao;
    private final CountDownLatch signal = new CountDownLatch(1);

    @Before
    public void setUp() throws Exception {
        Application application = RuntimeEnvironment.application;
        DaoTestComponent testComponent = DaggerDaoTestComponent.builder().appModule(new AppModule(application)).daoTestModule(new DaoTestModule()).build();
        testComponent.inject(this);

    }

    @Test
    public void daoShouldInvokeCallbacks() throws Exception {


        dao.loadWeather(new LatLng(0,0),new Response.Listener<ModelWeather>() {
            @Override
            public void onResponse(ModelWeather response) {
                System.out.println("response " + response);
                signal.countDown();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error  " + error.toString());
                signal.countDown();
            }
        });


        try {
            System.out.println("waiting 30secs..");
            signal.await(30, TimeUnit.SECONDS); // wait for callback
            if (signal.getCount() > 0)
                fail("Response was not delivered within timeout");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}