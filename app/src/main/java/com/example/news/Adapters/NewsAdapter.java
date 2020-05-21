package com.example.news.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.db.News;
import com.example.news.utils.JSONUtils;
import com.example.news.utils.NetworkUtils;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public List<News> news = JSONUtils.getNewsFromJSON(NetworkUtils.getJSONFromNetwork());

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_elements, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news1 = news.get(position);
        ImageView imageView = holder.imageViewPoster; //get imageview
        Picasso.get().load(news1.getBigPosterPath()).into(imageView); // download  image
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewPoster;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }
    }
}

