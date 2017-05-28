package bepoland.piotr.com.bepolandtest.app.details;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesViewModel;
import bepoland.piotr.com.bepolandtest.databinding.FragmentDetailBinding;
import bepoland.piotr.com.bepolandtest.databinding.WeatherPanelBinding;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by piotr on 18/05/17.
 */
public class CityDetailFragment extends BaseFragment {

    private FragmentDetailBinding fragmentDetailBinding;
    private CitiesViewModel citiesViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Activity activity) {

        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        citiesViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get
                (CitiesViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String cityData = bundle.getString("city");
        final ModelCity city = ModelCity.fromJson(cityData);
        fragmentDetailBinding.setCity(city);
        WeatherPanelBinding weatherPanelBinding = DataBindingUtil.inflate(LayoutInflater.from
                (getActivity()), R.layout.weather_panel, fragmentDetailBinding.detailsContainer,
                true);
        weatherPanelBinding.setWeather(city.getWeather());
        fragmentDetailBinding.btnForecast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                fragmentDetailBinding.btnForecast.setEnabled(false);
                citiesViewModel.loadForecast(city).observe(CityDetailFragment.this, new
                        Observer<ModelWeather[]>() {

                    @Override
                    public void onChanged(@Nullable ModelWeather[] modelWeathers) {

                        if (modelWeathers != null) {
                            publishData(modelWeathers);
                            forecastLoaded();
                        } else {
                            forecastError();
                        }
                    }
                });
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                parent, false);
        if (!getMainActivity().isTablet()) {
            getMainActivity().setSupportActionBar(fragmentDetailBinding.toolbar);
            getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            fragmentDetailBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    getActivity().onBackPressed();
                }
            });
        }
        return fragmentDetailBinding.getRoot();
    }

    public void publishData(ModelWeather[] weathers) {

        for (int i = 0; i < weathers.length; i++) {
            WeatherPanelBinding weatherPanelBinding = DataBindingUtil.inflate(LayoutInflater.from
                    (getActivity()), R.layout.weather_panel, fragmentDetailBinding
                    .detailsContainer, true);
            weatherPanelBinding.setWeather(weathers[i]);
        }
    }

    public void forecastLoaded() {

        Snackbar mySnackbar = Snackbar.make(fragmentDetailBinding.getRoot(), "5 days forecast " +
                "loaded.", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("OK", null);
        mySnackbar.show();
        fragmentDetailBinding.btnForecast.setVisibility(View.GONE);
    }

    public void forecastError() {

        Snackbar mySnackbar = Snackbar.make(fragmentDetailBinding.getRoot(), "Forecast cant be "
                + "loaded", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("OK", null);
        mySnackbar.show();
        fragmentDetailBinding.btnForecast.setEnabled(true);
    }
}