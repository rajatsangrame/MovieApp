package com.example.rajat.movie.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.rajat.movie.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Long, Movie> {

    public MutableLiveData<MovieDataSource> movieLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Long, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource();
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }
}
