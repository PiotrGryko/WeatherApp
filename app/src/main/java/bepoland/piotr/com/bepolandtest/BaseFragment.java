package bepoland.piotr.com.bepolandtest;

import android.arch.lifecycle.LifecycleFragment;
import android.support.v4.app.Fragment;

/**
 * Created by piotr on 20/05/17.
 */
public class BaseFragment extends LifecycleFragment {


    public MainActivity getMainActivity() {
        return (MainActivity)getActivity();
    }
}
