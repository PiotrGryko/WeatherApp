package bepoland.piotr.com.bepolandtest.app.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;

/**
 * Created by piotr on 21/05/17.
 */
public class FragmentSettings extends BaseFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_settings,parent,false);
        return v;
    }

}
