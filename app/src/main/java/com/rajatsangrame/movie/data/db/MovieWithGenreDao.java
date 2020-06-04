package com.rajatsangrame.movie.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieWithGenreDao {

    @Query("SELECT * from movie")
    public List<MovieWithGenre> getUsersWithRepos();

}
