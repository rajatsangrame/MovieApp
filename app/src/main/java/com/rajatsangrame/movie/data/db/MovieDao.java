package com.rajatsangrame.movie.data.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<MovieDB> movieDBList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(MovieDB movieDB);

    @Query("SELECT * FROM movie WHERE fetch_category = :category ORDER BY popularity DESC")
    DataSource.Factory<Integer, MovieDB> getDataSource(String category);
}
