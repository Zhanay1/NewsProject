package com.example.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.news.Adapters.NewsAdapter;
import com.example.news.db.News;
import com.example.news.db.NewsViewModel;
import com.example.news.utils.JSONUtils;
import com.example.news.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    public static NewsAdapter newsAdapter;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    private NewsViewModel newsViewModel;
    private static int page = 1;

    private int getColumnCount(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return  width / 185;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        preferences = getApplicationContext().getSharedPreferences("Favorite", 0); // 0 - for private mode
        editor = preferences.edit();

        recyclerViewPosters = findViewById(R.id.recyclerViewNews);
        if(getColumnCount() >= 3) {
            recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,getColumnCount()));
        }
        else {
            recyclerViewPosters.setLayoutManager(new GridLayoutManager(this,1));
        }
        newsAdapter = new NewsAdapter();
        recyclerViewPosters.setAdapter(newsAdapter);

        newsAdapter.setClickOnPosterListener(new NewsAdapter.ClickOnPosterListener() {
            @Override
            public void ClickOnPoster(int position) {
                News news = newsAdapter.news.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id",news.getId());
                startActivity(intent);
            }
        });
        newsAdapter.setClickOnFavoriteListener(new NewsAdapter.ClickOnFavoriteListener() {
            @Override
            public void ClickOnFavorite(int position) {
                if (preferences.contains(String.valueOf(newsAdapter.news.get(position).getId()))) {
                    editor.remove(String.valueOf(newsAdapter.news.get(position).getId()));
                    newsAdapter.ChangeFavorite(R.drawable.favourite_add_to);
                    newsAdapter.notifyItemChanged(position);
                    editor.commit();
                } else {
                    editor.putInt(String.valueOf(newsAdapter.news.get(position).getId()), newsAdapter.news.get(position).getId());
                    newsAdapter.ChangeFavorite(R.drawable.favourite_remove);
                    newsAdapter.notifyItemChanged(position);
                    editor.commit();
                }
            }
        });
        newsAdapter.setOnReachEndListener(new NewsAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                DownloadData();
            }
        });
        if(checkInternet()) {
            DownloadData();
        }
        LiveData<List<News>> newsFromLiveData = newsViewModel.getNews();
        newsFromLiveData.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news2) {
                newsAdapter.setNews(news2);
            }
        });
    }
    public boolean checkFavorite(int id){
        if(preferences.contains(String.valueOf(id))){
            return true;
        }
        else{
            return false;
        }
    }
    private void DownloadData(){
        List<News> news2 = JSONUtils.getNewsFromJSON(NetworkUtils.getJSONFromNetwork(page));
        if(page == 1 && !news2.isEmpty()) {
            newsViewModel.DeleteAllNews();
            }
            if(!news2.isEmpty()){
                for (News news1 : news2) {
                    newsViewModel.insertNews(news1);
                }
                page++;
            }
//        newsAdapter.news = news2;
    }
    private boolean checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
        }
        return connected;
    }
}
