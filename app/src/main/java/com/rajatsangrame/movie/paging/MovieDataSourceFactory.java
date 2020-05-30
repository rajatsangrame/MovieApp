package com.rajatsangrame.movie.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.util.Genre;

public class MovieDataSourceFactory extends DataSource.Factory<Long, Movie> {

    private MovieDataSourceFactory() {
    }

    private Genre restApiType;

    public MovieDataSourceFactory(Genre restApiType) {
        this.restApiType = restApiType;
    }

    public MutableLiveData<MovieDataSource> movieLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Long, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(restApiType); //  passing to the adapter
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }
}
