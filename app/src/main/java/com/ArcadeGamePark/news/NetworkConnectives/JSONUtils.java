package com.ArcadeGamePark.news.NetworkConnectives;

import com.ArcadeGamePark.news.Data.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    private static final String KEY_RESULTS = "results";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_AVERAGE = "vote_average";

    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String BASE_POSTER_SIZE = "w780";

    public static ArrayList<News> getNewsFromJSONObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        ArrayList<News> result = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt(KEY_ID);
                String title = object.getString(KEY_TITLE);
                String overview = object.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + BASE_POSTER_SIZE + object.getString(KEY_POSTER_PATH);
                String releaseDate = object.getString(KEY_RELEASE_DATE);
                double voteAverage = object.getDouble(KEY_VOTE_AVERAGE);
                News news = new News(id, title, overview, releaseDate, voteAverage, posterPath);
                result.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
