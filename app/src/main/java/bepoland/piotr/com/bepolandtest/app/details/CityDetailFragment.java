package bepoland.piotr.com.bepolandtest.app.details;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.App;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.databinding.FragmentDetailBinding;

/**
 * Created by piotr on 18/05/17.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class CityDetailFragment extends Fragment implements CityDetailContract.View {

    private FragmentDetailBinding fragmentDetailBinding;
    @Inject
    CityDetailPresenter userDetailPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerCityDetailComponent.builder().daoComponent(((App) getActivity().getApplication())
                .getDaoComponent()).cityDetailModule(new CityDetailModule(this)).build().inject
                (this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, parent, false);
        Bundle bundle = getArguments();
        ModelCity city = (ModelCity) bundle.getSerializable("city");
        return fragmentDetailBinding.getRoot();
    }

    @Override
    public void publishData(ModelCity city) {
    }
}