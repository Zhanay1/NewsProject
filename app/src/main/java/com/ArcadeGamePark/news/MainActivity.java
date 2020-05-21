package com.ArcadeGamePark.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ArcadeGamePark.news.Adapters.NewsAdapter;
import com.ArcadeGamePark.news.Data.News;
import com.ArcadeGamePark.news.NetworkConnectives.JSONUtils;
import com.ArcadeGamePark.news.NetworkConnectives.URLConnectives;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.i("Result", URLConnectives.buildURL().toString());
//        Log.i("Result", URLConnectives.getJSONFromURL().toString());
//        ArrayList<News> news = JSONUtils.getNewsFromJSONObject(URLConnectives.getJSONFromURL());
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < news.size(); i++) {
//            builder.append(news.get(i).getTitle() + ", ");
//        }
//        Log.i("Result", builder.toString());

        newsAdapter = new NewsAdapter();
        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(newsAdapter);
    }
}
