package bepoland.piotr.com.bepolandtest.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by piotr on 22/05/17.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private String TAG = DownloadImageTask.class.getName();
    private ImageView bmImage;
    private String url;

    public DownloadImageTask(ImageView bmImage, String url) {
        this.bmImage = bmImage;
        this.url = url;
    }

    public void start() {
        Bitmap bitmap = BitmapCache.getInstance().getBitmapFromMemCache(url);
        if (bitmap != null) {
            Log.d(TAG, "bitmap fetched from cache");
            bmImage.setImageBitmap(bitmap);
        } else
            execute();
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap result = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            result = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        BitmapCache.getInstance().addBitmapToMemoryCache(url, result);
        return result;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}