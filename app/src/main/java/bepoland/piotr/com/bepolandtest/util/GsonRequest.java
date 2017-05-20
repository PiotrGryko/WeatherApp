package bepoland.piotr.com.bepolandtest.util;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Created by piotr on 11/05/17.
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Response.Listener<T> listener;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * Makes json object flat so GSON could handle it. Iterates over json object, extracts nested data and writes it to result object
     * @param object object to process
     * @param result flat result
     * @return
     */
    private JSONObject deserializeJsonObject(JSONObject object, JSONObject result) {
        try {
            Iterator<String> keys = object.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = object.get(key);
                if (value instanceof JSONArray) {
                    JSONArray array = (JSONArray) value;
                    for (int i = 0; i < array.length(); i++) {
                        Object arrayValue = array.get(i);
                        if (arrayValue instanceof JSONObject) {
                            deserializeJsonObject((JSONObject) arrayValue, result);
                        } else {result.put(key, value);}
                    }
                } else if (value instanceof JSONObject) {
                    result = deserializeJsonObject((JSONObject) value, result);
                } else {
                    result.put(key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String deserialize(String data) {
        JSONObject result = new JSONObject();
        try {
            JSONObject object = new JSONObject(data);
            deserializeJsonObject(object, result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            json = deserialize(json);
            Log.d("XXX", "deserialized data " + json);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}