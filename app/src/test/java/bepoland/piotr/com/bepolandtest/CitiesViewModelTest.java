package bepoland.piotr.com.bepolandtest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import bepoland.piotr.com.bepolandtest.app.model.ModelCity;
import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesRepository;
import bepoland.piotr.com.bepolandtest.app.viewmodel.CitiesViewModel;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by piotr on 28/05/17.
 */

@RunWith(JUnit4.class)
public class CitiesViewModelTest {

    private CitiesViewModel viewModel;
    private CitiesRepository repository;

    @Before
    public void setUp()
    {
        repository = Mockito.mock(CitiesRepository.class);
        viewModel = new CitiesViewModel(repository);
    }

    @Test
    public void testAddCity()
    {
        LatLng position = new LatLng(0,0);
        ArgumentCaptor<LatLng> captor = ArgumentCaptor.forClass(LatLng.class);
        viewModel.addCity(position);
        Mockito.verify(repository).addLocation(captor.capture());
        assertThat(captor.getValue(), is(position));
    }

    @Test
    public void testRemoveCity()
    {

        ModelCity city = Mockito.mock(ModelCity.class);
        ArgumentCaptor<ModelCity> captor = ArgumentCaptor.forClass(ModelCity.class);
        viewModel.removeCity(city);
        Mockito.verify(repository).removeElement(captor.capture());
        assertThat(captor.getValue(), is(city));

    }
    @Test
    public void testGetCities()
    {
        viewModel.getCities();
        Mockito.verify(repository).loadCities();

    }

    @Test
    public void testLoadForecast()
    {
        ModelCity city = Mockito.mock(ModelCity.class);
        ArgumentCaptor<ModelCity> captor = ArgumentCaptor.forClass(ModelCity.class);
        viewModel.loadForecast(city);
        Mockito.verify(repository).loadForecast(captor.capture());
        assertThat(captor.getValue(), is(city));

    }


}
