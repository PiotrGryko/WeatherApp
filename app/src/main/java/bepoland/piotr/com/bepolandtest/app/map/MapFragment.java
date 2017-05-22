package bepoland.piotr.com.bepolandtest.app.map;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;
import bepoland.piotr.com.bepolandtest.app.model.ModelCity;

/**
 * Created by piotr on 20/05/17.
 */
public class MapFragment extends BaseFragment implements MapContract.View, PlaceSelectionListener {

    private MapView mapView;
    private GoogleMap gMap;
    private MapPresenter presenter;
    private SupportPlaceAutocompleteFragment autocompleteFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MapPresenter(this, getMyApplication().provideGeocoder(), getMyApplication
                ().provideDao(), getMyApplication().provideDatabaseHelper());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, parent, false);
        autocompleteFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager()
                .findFragmentById(R.id.place_fragment);
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (mapView != null) {
            mapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    initGoogleMap(googleMap);
                    presenter.loadData();
                    autocompleteFragment.setOnPlaceSelectedListener(MapFragment.this);
                }
            });
        }
        return v;
    }

    private void initGoogleMap(GoogleMap googleMap) {
        this.gMap = googleMap;
        this.gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                Snackbar mySnackbar = Snackbar.make(getView().findViewById(R.id.parentPanel),
                        "Loading location...", Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) mySnackbar.getView();
                snack_view.addView(new ProgressBar(getActivity()));
                mySnackbar.show();
                presenter.addLocation(latLng);
            }
        });
    }

    @Override
    public void onPlaceSelected(Place place) {
        presenter.addLocation(place.getLatLng());
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 17));
    }

    @Override
    public void onError(Status status) {
    }

    @Override
    public void locationAdded() {
        Snackbar mySnackbar = Snackbar.make(getView().findViewById(R.id.parentPanel),
                "Weather loaded.", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    @Override
    public void locationError() {
        Snackbar mySnackbar = Snackbar.make(getView().findViewById(R.id.parentPanel),
                "Weather can`t be loaded, try again.", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    @Override
    public void publishData(ModelCity[] data) {
        this.gMap.clear();
        for (int i = 0; i < data.length; i++) {
            this.gMap.addMarker(new MarkerOptions().position(new LatLng(data[i].getLat(), data[i]
                    .getLon())));
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
