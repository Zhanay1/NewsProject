package com.example.news.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsViewModel extends AndroidViewModel {

    private static NewsDatabase database;
    private LiveData<List<News>> news;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        database = NewsDatabase.getInstance(getApplication());
        news = database.newsDao().getAllNews();
    }
    public News getNewsById(int id){
        try {
            return new GetNewsTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteAllNews(){
        new DeleteNewsTask().execute();
    }
    public void insertNews(News news){
        new InsertNewTask().execute(news);
    }
    public void deleteNews(News news){
        new DeleteNewTask().execute(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }

    private static class DeleteNewTask extends AsyncTask<News, Void, Void>{

        @Override
        protected Void doInBackground(News... news) {
            if(news != null &&  news.length > 0){
                database.newsDao().DeleteNews(news[0]);
            }
            return null;
        }
    }

    private static class InsertNewTask extends AsyncTask<News, Void, Void>{

        @Override
        protected Void doInBackground(News... news) {
            if(news != null &&  news.length > 0){
                database.newsDao().insertNews(news[0]);
            }
            return null;
        }
    }

    private static class DeleteNewsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... integers) {
               database.newsDao().DeleteAllNews();
                return null;
        }
    }

    private static class GetNewsTask extends AsyncTask<Integer, Void, News>{

        @Override
        protected News doInBackground(Integer... integers) {
            if(integers != null &&  integers.length > 0){
                return database.newsDao().getNewsById(integers[0]);
            }
            return null;
        }
    }
}
