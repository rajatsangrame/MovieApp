package com.rajatsangrame.movie.data.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<TVDB> tvdbList);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY popularity DESC")
    DataSource.Factory<Integer, TVDB> getPopularTVDataSource(String category);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY vote_average DESC")
    DataSource.Factory<Integer, TVDB> getTopRatedTVDataSource(String category);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY entry_timestamp ASC")
    DataSource.Factory<Integer, TVDB> getDataSource(String category);

    @Query("SELECT * FROM tv WHERE saved = 1")
    List<TVDB> getAllSaved();

    @Query("SELECT  * FROM tv WHERE id = :id")
    TVDB getMovieFromId(int id);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TVDB tvdb);
}
