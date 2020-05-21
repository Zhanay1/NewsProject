package com.example.news.utils;

import com.example.news.db.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    private static final String KEY_RESULTS = "results";
    //objects in results
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    //poster
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<News> getNewsFromJSON(JSONObject jsonObject){ //  we get array movies
        ArrayList<News> result = new ArrayList<>();
        if(jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject objectNews = jsonArray.getJSONObject(i);
                int id = objectNews.getInt(KEY_ID);
                String title = objectNews.getString(KEY_TITLE);
                String overview = objectNews.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectNews.getString(KEY_POSTER_PATH);
                double vote_average = objectNews.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectNews.getString(KEY_RELEASE_DATE);
                // make object to get data from class News
                News news = new News(id, title,posterPath,overview, vote_average, releaseDate);
                result.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
