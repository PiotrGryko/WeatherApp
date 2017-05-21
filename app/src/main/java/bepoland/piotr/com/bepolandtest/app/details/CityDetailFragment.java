package bepoland.piotr.com.bepolandtest.app.details;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.App;
import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.databinding.FragmentDetailBinding;
import bepoland.piotr.com.bepolandtest.databinding.WeatherPanelBinding;

/**
 * Created by piotr on 18/05/17.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class CityDetailFragment extends BaseFragment implements CityDetailContract.View {

    private FragmentDetailBinding fragmentDetailBinding;
    @Inject
    CityDetailPresenter cityDetailPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCityDetailComponent.builder().daoComponent(((App) getActivity().getApplication())
                .getDaoComponent()).cityDetailModule(new CityDetailModule(this)).build().inject
                (this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String cityData = bundle.getString("city");
        final ModelCity city = ModelCity.fromJson(cityData);
        fragmentDetailBinding.setCity(city);
        WeatherPanelBinding weatherPanelBinding = DataBindingUtil.inflate(LayoutInflater.from
                        (getActivity()), R.layout.weather_panel, fragmentDetailBinding
                        .detailsContainer,
                true);
        weatherPanelBinding.setWeather(city.getWeather());
        fragmentDetailBinding.btnForecast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragmentDetailBinding.btnForecast.setEnabled(false);
                cityDetailPresenter.loadForecast(city);

            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                parent, false);
        getMainActivity().setSupportActionBar(fragmentDetailBinding.toolbar);
        getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentDetailBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return fragmentDetailBinding.getRoot();
    }

    @Override
    public void publishData(ModelWeather[] weathers) {
        for (int i = 0; i < weathers.length; i++) {
            WeatherPanelBinding weatherPanelBinding = DataBindingUtil.inflate(LayoutInflater.from
                    (getActivity()), R.layout.weather_panel, fragmentDetailBinding
                    .detailsContainer, true);
            weatherPanelBinding.setWeather(weathers[i]);
        }
    }

    @Override
    public void forecastLoaded() {
        Snackbar mySnackbar = Snackbar.make(fragmentDetailBinding.getRoot(),
                "5 days forecast loaded.", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("OK", null);
        mySnackbar.show();
        fragmentDetailBinding.btnForecast.setVisibility(View.GONE);
    }

    @Override
    public void forecastError() {
        Snackbar mySnackbar = Snackbar.make(fragmentDetailBinding.getRoot(),
                "Forecast cant be loaded", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("OK", null);
        mySnackbar.show();
        fragmentDetailBinding.btnForecast.setEnabled(true);

    }
}