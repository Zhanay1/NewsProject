package com.example.news.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    LiveData<List<News>> getAllNews();

    @Query("SELECT * FROM news WHERE id == :newsID")
    News getNewsById(int newsID);

    @Query("DELETE FROM news")
    void DeleteAllNews();

    @Insert
    void insertNews(News news);

    @Delete
    void DeleteNews(News news);
}
