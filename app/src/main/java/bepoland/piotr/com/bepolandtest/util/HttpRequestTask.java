package bepoland.piotr.com.bepolandtest.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by piotr on 22/05/17.
 */
public abstract class HttpRequestTask<T> extends AsyncTask<Object,Object,String> {

    private String TAG = HttpRequestTask.class.getName();
    public interface OnRequestListener<T> {

        public void onSuccess(T data);
        public void onError(Exception error);
    }

    private String baseUrl;
    private OnRequestListener onRequestListener;
    private Handler handler;



    protected HttpRequestTask(String url, OnRequestListener onRequestListener) {
        this.baseUrl = url;
        this.onRequestListener = onRequestListener;
        this.handler = new Handler();
    }

    @Override
    protected String doInBackground(Object... uri) {
        Log.d(TAG,"perform http request "+baseUrl);
        URL url;
        StringBuilder total = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(baseUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (final Exception e) {
            e.printStackTrace();
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Log.d(TAG,"error "+e.toString());
                    onRequestListener.onError(e);
                }
            });
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        Log.d(TAG,"raw response "+total.toString());

        return parseNetworkResponse(total.toString());
    }

    public String parseNetworkResponse(String data)
    {
        return data;
    }
    public abstract T deliverResponse(String response);


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        onRequestListener.onSuccess(deliverResponse(result));
    }
}
