package bepoland.piotr.com.bepolandtest;

import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by piotr on 12/05/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ListFragmentTest {

    private CityListFragment cityListFragment;

    @Before
    public void setUp() {

        cityListFragment = new CityListFragment();
        SupportFragmentTestUtil.startVisibleFragment(cityListFragment);
    }

    @Test
    public void fragmentShouldNotBeNull() {
        assertNotNull("fragment is null!", cityListFragment);
    }

    @Test
    public void recyclerViewAdapterShouldBeSet() {
        assertNotNull("fragment view is null!", cityListFragment.getView());
        RecyclerView rv = (RecyclerView) cityListFragment.getView().findViewById(R.id.recycler_view);
        assertNotNull("RecyclerView is null!", rv);
        assertNotNull("Adapter is not set!", rv.getAdapter());
    }


}
