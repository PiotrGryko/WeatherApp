package bepoland.piotr.com.bepolandtest.app.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import bepoland.piotr.com.bepolandtest.di.ViewModelSubcomponent;

/**
 * Created by piotr on 27/05/17.
 */

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final ViewModelSubcomponent subcomponent;

    @Inject
    public CustomViewModelFactory(ViewModelSubcomponent subcomponent)
    {
        this.subcomponent=subcomponent;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T)subcomponent.citiesViewModel();
    }
}
