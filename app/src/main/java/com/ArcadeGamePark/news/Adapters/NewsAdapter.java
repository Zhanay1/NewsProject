package com.ArcadeGamePark.news.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ArcadeGamePark.news.Data.News;
import com.ArcadeGamePark.news.NetworkConnectives.JSONUtils;
import com.ArcadeGamePark.news.NetworkConnectives.URLConnectives;
import com.ArcadeGamePark.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<News> newsList = JSONUtils.getNewsFromJSONObject(URLConnectives.getJSONFromURL());

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        String posterPath = newsList.get(position).getPosterPath();
        ImageView imageViewPoster = holder.imageViewPoster;
        Picasso.get().load(posterPath).placeholder(R.drawable.loading_error).into(imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class NewsHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPoster;

        // Constructor
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }
    }

}
