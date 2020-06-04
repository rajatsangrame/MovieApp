package com.rajatsangrame.movie.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Created by Rajat Sangrame on 22/4/20.
 * http://github.com/rajatsangrame
 * <p>
 * Ref: https://medium.com/@tonyowen/room-entity-annotations-379150e1ca82
 */

@Entity(tableName = "genre_table")
public class GenreDB {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private int name;

}
