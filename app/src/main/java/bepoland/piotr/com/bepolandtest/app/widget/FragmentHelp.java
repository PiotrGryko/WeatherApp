package bepoland.piotr.com.bepolandtest.app.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import bepoland.piotr.com.bepolandtest.BaseFragment;
import bepoland.piotr.com.bepolandtest.R;

/**
 * Created by piotr on 21/05/17.
 */
public class FragmentHelp extends BaseFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_help,parent,false);
        WebView webView = (WebView)v.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getResources().getString(R.string.help_url));
        return v;
    }
}
