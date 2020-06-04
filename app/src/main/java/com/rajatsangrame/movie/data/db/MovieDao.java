package com.rajatsangrame.movie.data.db;

import androidx.lifecycle.LiveData;
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

//    @Query("SELECT * from movie ORDER BY timestamp DESC")
//    LiveData<List<MovieDB>> getAllMovies();
//
//    @Query("SELECT * from movie WHERE genre LIKE '%' || :genre || '%'")
//    LiveData<List<MovieDB>> getMoviesByGenre(String genre);
//
//    @Query("SELECT * from movie WHERE id=:id")
//    MovieDB get(String id);
//
//    @Query("SELECT * from movie WHERE is_saved=1 ORDER BY timestamp DESC")
//    LiveData<List<MovieDB>> getSavedMovies();
//
//    @Query("UPDATE movie SET timestamp = :timeStamp, is_saved = :isSaved WHERE id = :id")
//    int updateSave(String id, boolean isSaved, long timeStamp);
//
//    @Query("DELETE from movie WHERE is_saved=0")
//    void deleteUnsaved();
}
