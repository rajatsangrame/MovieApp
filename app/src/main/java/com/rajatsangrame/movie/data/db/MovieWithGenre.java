package com.rajatsangrame.movie.data.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.rajatsangrame.movie.data.db.movie.MovieDB;

import java.util.List;

/**
 * Created by Rajat Sangrame on 4/6/20.
 * http://github.com/rajatsangrame
 */
class MovieWithGenre {

    @Embedded
    public MovieDB movieDB;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<GenreDB> genreDBList;

}
