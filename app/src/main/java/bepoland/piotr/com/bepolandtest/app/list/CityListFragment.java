package bepoland.piotr.com.bepolandtest.app.list;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.details.CityDetailFragment;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesViewModel;
import bepoland.piotr.com.bepolandtest.databinding.FragmentListBinding;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by piotr on 18/05/17.
 */
public class CityListFragment extends BaseFragment{


    private FragmentListBinding fragmentListBinding;
    private CityListAdapter adapter;
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

        citiesViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(CitiesViewModel.class);
        citiesViewModel.getCities().observe(this,new Observer<ModelCity[]>() {

            @Override
            public void onChanged(@Nullable ModelCity[] modelCities) {
                publishData(modelCities);
            }
        });

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CityListAdapter(new ModelCity[0]);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, parent,
                false);
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragmentListBinding.recyclerView.setLayoutManager(layoutManager);
        fragmentListBinding.recyclerView.setHasFixedSize(true);
        fragmentListBinding.recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (fragmentListBinding.recyclerView.getContext(), layoutManager.getOrientation());
        fragmentListBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.setOnItemClickListener(new CityListAdapter.OnCityClickListener() {

            @Override
            public void onCityClick(ModelCity city, ImageView profile) {

                showDetails(city, profile);
            }

            @Override
            public void onElementRemoved(ModelCity city) {
                citiesViewModel.removeCity(city).observe(CityListFragment.this, new Observer<ModelCity>() {

                    @Override
                    public void onChanged(@Nullable ModelCity city) {
                        elementRemoved(city);
                    }
                });
            }
        });

    }

    private void refreshLabel()
    {
        if (adapter.getItemCount() > 0) fragmentListBinding.tvLabel.setVisibility(View.GONE);
        else fragmentListBinding.tvLabel.setVisibility(View.VISIBLE);
    }


    public void publishData(ModelCity[] data) {

        adapter.setData(data);
        adapter.notifyDataSetChanged();
        refreshLabel();
    }


    public void elementRemoved(ModelCity city) {

        Snackbar mySnackbar = Snackbar.make(fragmentListBinding.getRoot(), "Element "
                + city.getName() + " removed", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
        refreshLabel();
    }


    public void showDetails(ModelCity city, ImageView image) {

        Bundle bundle = new Bundle();
        bundle.putString("city", city.toJson());
        CityDetailFragment fragment = new CityDetailFragment();
        fragment.setArguments(bundle);
        getMainActivity().replaceFragmentWithTransition(fragment, image, "test");
    }
}
