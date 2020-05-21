package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.news.Adapters.NewsAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewNews);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 1));
        newsAdapter = new NewsAdapter();
        recyclerViewPosters.setAdapter(newsAdapter);
//        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork();
//        ArrayList<News> news = JSONUtils.getNewsFromJSON(jsonObject);
//        StringBuilder builder = new StringBuilder();
//        for(int i = 0;i < news.size();i++){
//            builder.append(news.get(i).getTitle());
//        }
//        Log.i("MyResult", builder.toString());
    }
}
