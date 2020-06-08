package com.rajatsangrame.movie.data.db.tv;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.rajatsangrame.movie.data.db.movie.MovieDB;

import java.util.List;

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(List<TVDB> tvdbList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(TVDB tvdb);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY popularity DESC, entry_timestamp ASC")
    DataSource.Factory<Integer, TVDB> getPopularTVDataSource(String category);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY vote_average DESC, entry_timestamp ASC")
    DataSource.Factory<Integer, TVDB> getTopRatedTVDataSource(String category);

    @Query("SELECT * FROM tv WHERE fetch_category = :category ORDER BY entry_timestamp ASC")
    DataSource.Factory<Integer, TVDB> getDataSource(String category);

    @Query("SELECT * FROM tv WHERE saved = 1 ORDER BY saved_time DESC")
    LiveData<List<TVDB>> getAllSaved();

    @Query("SELECT  * FROM tv WHERE id = :id")
    LiveData<TVDB> getLiveMovieFromId(int id);

    @Query("SELECT  * FROM tv WHERE id = :id")
    TVDB getTVFromId(int id);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TVDB tvdb);
}
