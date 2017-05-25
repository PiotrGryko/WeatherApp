package bepoland.piotr.com.bepolandtest.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;

import bepoland.piotr.com.bepolandtest.app.model.ModelWeather;

/**
 * Created by piotr on 25/05/17.
 *
 * TODO refactor it to useo only GSON provided JsonObject.class
 */

public class WeatherResponseDeserializer {

    private static JSONObject deserializeJsonObject(JSONObject object, JSONObject result) {

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

    private static  String deserializeObject(String data) {

        JSONObject result = new JSONObject();
        try {
            JSONObject object = new JSONObject(data);
            deserializeJsonObject(object, result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String deserializeArray(String data) {

        JSONArray result = new JSONArray();
        JSONObject response = null;
        try {
            response = new JSONObject(data);
            JSONArray list = response.has("list") ? response.getJSONArray("list") : new JSONArray();
            for (int i = 0; i < list.length(); i++) {
                JSONObject listElement = list.getJSONObject(i);
                JSONObject flatElement = new JSONObject(deserializeObject(listElement.toString()));
                result.put(flatElement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static  class WeatherArrayDeserializer implements JsonDeserializer<ModelWeather[]> {

        @Override
        public ModelWeather[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            String result = deserializeArray(json.toString());
            return new Gson().fromJson(result, typeOfT);
        }
    }

    public static class WeatherObjectDeserializer implements JsonDeserializer<ModelWeather> {

        @Override
        public ModelWeather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String result = deserializeObject(json.toString());
            return new Gson().fromJson(result, typeOfT);
        }
    }
}
