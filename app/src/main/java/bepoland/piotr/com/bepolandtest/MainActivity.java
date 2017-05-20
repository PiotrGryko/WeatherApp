package bepoland.piotr.com.bepolandtest;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;

import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;
import bepoland.piotr.com.bepolandtest.app.map.MapFragment;
import bepoland.piotr.com.bepolandtest.util.DetailsTransition;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maybeInitPhoneNavigation();
        if (savedInstanceState == null) {
            if (!isTablet()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragments_container, new
                        CityListFragment()).commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragments_container, new
                        MapFragment()).commitAllowingStateLoss();
            }
        }
    }

    private void maybeInitPhoneNavigation() {
        if (!isTablet()) {
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    switch (position) {
                        case 0: {
                            replaceFragment(new CityListFragment(), false);
                            break;
                        }
                        case 1: {
                            replaceFragment(new MapFragment(), false);
                            break;
                        }
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager
                    .OnBackStackChangedListener() {

                @Override
                public void onBackStackChanged() {
                    int count = getSupportFragmentManager().getBackStackEntryCount();
                    if (count > 0) {

                        tabLayout.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        setSupportActionBar(toolbar);
                    } else {
                        tabLayout.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

    }



    public void replaceFragmentWithTransition(Fragment fragment, View view, String transitionName) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragments_container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            current.setExitTransition(new Fade());
            fragment.setAllowEnterTransitionOverlap(false);
            fragment.setAllowReturnTransitionOverlap(true);
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(view, transitionName)
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, boolean backstack) {
        if (backstack) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,
                    fragment).addToBackStack(null).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,
                    fragment).commitAllowingStateLoss();
        }
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
