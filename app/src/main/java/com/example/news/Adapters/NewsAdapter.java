package com.example.news.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.MainActivity;
import com.example.news.db.News;
import com.example.news.utils.JSONUtils;
import com.example.news.utils.NetworkUtils;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public List<News> news;
    private ClickOnPosterListener clickOnPosterListener;
    private ClickOnFavoriteListener clickOnFavoriteListener;
    private onReachEndListener onReachEndListener;;
    private int FavoriteImgResources;

    public NewsAdapter (){
        news = new ArrayList<>();
    }

    public void setNews(List<News> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public void ChangeFavorite(int imageResource){
        FavoriteImgResources = imageResource;
    }

    public interface ClickOnPosterListener{
        void ClickOnPoster(int position); //to click the poster image
    }
    public interface onReachEndListener{
        void onReachEnd(); // method call when we come end of page
    }

    public void setOnReachEndListener(NewsAdapter.onReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }
    public void setClickOnPosterListener(ClickOnPosterListener clickOnPosterListener) { //setter
        this.clickOnPosterListener = clickOnPosterListener;
    }

    public interface ClickOnFavoriteListener{
        void ClickOnFavorite(int position); //to click the poster image
    }
    public void setClickOnFavoriteListener(ClickOnFavoriteListener clickOnFavoriteListener) { //setter
        this.clickOnFavoriteListener = clickOnFavoriteListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_elements, parent, false);
        return new NewsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (news.size() >= 20 && position > news.size() - 4 && onReachEndListener != null){///when we come end of page and onReachEndListener not null
            onReachEndListener.onReachEnd();
        }
        News news1 = news.get(position);
        ImageView imageView = holder.imageViewPoster; //get imageview
        ImageView imageViewFavorite = holder.imageViewFavorite;
        imageView.setClipToOutline(true);
        Picasso.get().load(news1.getBigPosterPath()).placeholder(R.drawable.placeholder_large).into(imageView); // download  image
        TextView textViewTitle = holder.textViewTitle;
        TextView textViewRating = holder.textViewRating;
        TextView textViewReleaseDate = holder.textViewReleaseDate;
        textViewTitle.setText(news1.getTitle());
        textViewRating.setText(String.valueOf(news1.getVoteAverage()));
        textViewReleaseDate.setText(news1.getReleaseDate());
        if(MainActivity.preferences.contains(String.valueOf(news.get(position).getId()))){
            FavoriteImgResources = R.drawable.favourite_remove;
        }
        else{
            FavoriteImgResources = R.drawable.favourite_add_to;
        }
        imageViewFavorite.setImageResource(FavoriteImgResources);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        public List<News> news;

        private ImageView imageViewPoster;
        private TextView textViewRating;
        private TextView textViewTitle;
        private TextView textViewReleaseDate;
        private ImageView imageViewFavorite;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            itemView.setOnClickListener(new View.OnClickListener() { // when the click image
                @Override
                public void onClick(View v) {
                    if(clickOnPosterListener != null){
                        clickOnPosterListener.ClickOnPoster(getAdapterPosition());
                    }
                }
            });
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewReleaseDate = itemView.findViewById(R.id.textViewReleaseDate);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageViewFavorite = itemView.findViewById(R.id.imageViewAddToFavourite);
            imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickOnFavoriteListener != null){
                        clickOnFavoriteListener.ClickOnFavorite(getAdapterPosition());
                    }
                }
            });
        }
    }
}

