package bepoland.piotr.com.bepolandtest.app.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.App;
import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.data.component.DaoComponent;
import bepoland.piotr.com.bepolandtest.databinding.FragmentListBinding;

/**
 * Created by piotr on 18/05/17.
 */
public class CityListFragment extends BaseFragment implements CityListContract.View {

    @Inject
    CityListPresenter fragmentListPresenter;
    private FragmentListBinding fragmentListBinding;
    private CityListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoComponent daoComponent = ((App) getActivity().getApplication()).getDaoComponent();
        DaggerCityListComponent.builder().daoComponent(daoComponent).cityListModule(new
                CityListModule(this)).build().inject(this);
        adapter = new CityListAdapter(new ModelCity[0]);
        //fragmentListPresenter.loadData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, parent, false);
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentListBinding.recyclerView.setHasFixedSize(true);
        fragmentListBinding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CityListAdapter.OnCityClickListener() {

            @Override
            public void onCityClick(ModelCity city, ImageView profile) {
                showDetails(city, profile);
            }
        });

    }

    @Override
    public void publishData(ModelCity[] data) {



        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDetails(ModelCity city, ImageView image) {

        /*
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        CityDetailFragment fragment = new CityDetailFragment();
        fragment.setArguments(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());

            fragment.setAllowEnterTransitionOverlap(false);
            fragment.setAllowReturnTransitionOverlap(true);
            fragment.setSharedElementReturnTransition(new DetailsTransition());

        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(image, "test")
                .replace(R.id.fragments_container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
        */
    }
}
