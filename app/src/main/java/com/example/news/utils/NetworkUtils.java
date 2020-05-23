package com.example.news.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie"; // request to network

    private static final String BASE_NEWS_URL = "https://api.themoviedb.org/3/movie/";

    //parameters from url
    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_PAGE = "page";
    //values from parameters
    private static final String API_KEY = "de2b045baa608f0fcb7dba0ff4d52c0f";

    private static URL buildURl(int page){
        URL result = null;
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page)).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildNewsURl(int id){
        URL result = null;
        Uri uri = Uri.parse(BASE_NEWS_URL + String.valueOf(id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetwork (int page){  //get JSON data from network
        JSONObject result = null;
        URL url = buildURl(page); // get variables
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONNewsFromNetwork (int id){  //get JSON data from network
        JSONObject result = null;
        URL url = buildNewsURl(id); // get variables
        try {
            result = new JSONLoadNewsTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static class JSONLoadTask extends AsyncTask<URL,Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null){ // if line not null we add line to builder
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }

    private static class JSONLoadNewsTask extends AsyncTask<URL,Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null){ // if line not null we add line to builder
                    builder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }
}
