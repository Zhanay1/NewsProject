package com.example.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.db.News;
import com.example.news.db.NewsViewModel;
import com.example.news.utils.JSONUtils;
import com.example.news.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewRating;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ImageView imageViewFavorite;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private News news;
    private NewsViewModel viewModel;

    private int id;
    private Object MenuItem;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewRating = findViewById(R.id.textViewRating);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewFavorite = findViewById(R.id.imageViewAddToFavourite);
        preferences = getApplicationContext().getSharedPreferences("Favorite", 0);
        editor = preferences.edit();
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("id")){
            id = intent.getIntExtra("id", -1);
        }else {
            finish();
        }
        news = viewModel.getNewsById(id);
//        news = JSONUtils.getNewsFromJSONObject(NetworkUtils.getJSONNewsFromNetwork(id));
        Picasso.get().load(news.getBigPosterPath()).into(imageViewBigPoster);
        textViewRating.setText(String.valueOf(news.getVoteAverage()));
        textViewTitle.setText(news.getTitle());
        textViewReleaseDate.setText(news.getReleaseDate());
        textViewOverview.setText(news.getOverview());
        getSupportActionBar().setTitle(news.getTitle());
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        if(preferences.contains(String.valueOf(news.getId()))){
            imageViewFavorite.setImageResource(R.drawable.favourite_remove);
        }
        else{
            imageViewFavorite.setImageResource(R.drawable.favourite_add_to);
        }
    }

    public void AddToFavorite(View view) {
        ImageView imageViewFavourite = view.findViewById(R.id.imageViewAddToFavourite);
        if (preferences.contains(String.valueOf(news.getId()))) {
            editor.remove(String.valueOf(news.getId()));
            imageViewFavourite.setImageResource(R.drawable.favourite_add_to);
            editor.commit();
            MainActivity.newsAdapter.notifyDataSetChanged();
        } else {
            editor.putInt(String.valueOf(news.getId()), news.getId());
            imageViewFavourite.setImageResource(R.drawable.favourite_remove);
            editor.commit();
            MainActivity.newsAdapter.notifyDataSetChanged();
        }
    }
}
