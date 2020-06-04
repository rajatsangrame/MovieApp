package com.rajatsangrame.movie.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.rajatsangrame.movie.data.model.home.Genre;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rajat Sangrame on 4/6/20.
 * http://github.com/rajatsangrame
 */
public class Converters {

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    private List<Genre> fromGenre(String genre) {

        Genre[] array = new Gson().fromJson(genre, Genre[].class);
        return Arrays.asList(array);
    }

    @TypeConverter
    public String genreToString(List<Genre> list) {
        return new Gson().toJson(list);
    }

}

