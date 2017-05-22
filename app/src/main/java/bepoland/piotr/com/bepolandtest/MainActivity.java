package bepoland.piotr.com.bepolandtest;

import android.Manifest;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;
import bepoland.piotr.com.bepolandtest.app.map.MapFragment;
import bepoland.piotr.com.bepolandtest.app.widget.FragmentHelp;
import bepoland.piotr.com.bepolandtest.app.widget.FragmentSettings;
import bepoland.piotr.com.bepolandtest.util.DetailsTransition;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maybeInitPhoneNavigation();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings: {
                replaceFragment(new FragmentSettings(), true);
                return true;
            }
            case R.id.action_help: {
                replaceFragment(new FragmentHelp(), true);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void maybeInitPhoneNavigation() {
        if (!isTablet()) {
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
                    } else {
                        tabLayout.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        ActivityCompat.requestPermissions(this,
                              new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                               123);

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
