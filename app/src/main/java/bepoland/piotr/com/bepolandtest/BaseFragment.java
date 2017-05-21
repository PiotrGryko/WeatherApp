package bepoland.piotr.com.bepolandtest;

import android.support.v4.app.Fragment;

/**
 * Created by piotr on 20/05/17.
 */
public class BaseFragment extends Fragment {


    public MainActivity getMainActivity() {
        return (MainActivity)getActivity();
    }
    public App getMyApplication()
    {
        return ((App)getActivity().getApplication());
    }
}
