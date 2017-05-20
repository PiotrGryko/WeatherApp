package bepoland.piotr.com.bepolandtest;

import android.support.v4.app.Fragment;

/**
 * Created by piotr on 20/05/17.
 */
public class BaseFragment extends Fragment {

    public boolean isTablet() {
        if (getActivity() instanceof MainActivity)
            return ((MainActivity) getActivity()).isTablet();
        else return false;
    }

    public MainActivity getMainActivity() {
        return (MainActivity)getActivity();
    }
}
