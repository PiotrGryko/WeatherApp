package bepoland.piotr.com.bepolandtest;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import bepoland.piotr.com.bepolandtest.app.list.CityListFragment;
import bepoland.piotr.com.bepolandtest.app.map.MapFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isTablet()) {

            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    switch (position) {
                        case 0:
                        {
                            replaceFragment(new CityListFragment());
                            break;
                        }
                        case 1:
                        {
                            replaceFragment(new MapFragment());
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
        }
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

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container,
                fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
