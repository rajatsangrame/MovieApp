package com.rajatsangrame.movie.data.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<MovieDB> movieDBList);

    @Query("SELECT * FROM movie WHERE fetch_category = :category ORDER BY popularity DESC")
    DataSource.Factory<Integer, MovieDB> getPopularDataSource(String category);

    @Query("SELECT * FROM movie WHERE fetch_category = :category ORDER BY vote_average DESC")
    DataSource.Factory<Integer, MovieDB> getTopRatedDataSource(String category);

    @Query("SELECT * FROM movie WHERE fetch_category = :category ORDER BY entry_timestamp ASC")
    DataSource.Factory<Integer, MovieDB> getDataSource(String category);

    @Query("SELECT * FROM movie WHERE saved = 1")
    List<MovieDB> getAllSaved();

    @Query("SELECT  * FROM movie WHERE id = :id")
    MovieDB getMovieFromId(int id);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(MovieDB movieDB);
}
