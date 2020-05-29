package com.rajatsangrame.movie.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.network.RetrofitClient;

public class MovieDataSourceFactory extends DataSource.Factory<Long, Movie> {

    private MovieDataSourceFactory() {
    }

    private RetrofitClient.GENRE restApiType;

    MovieDataSourceFactory(RetrofitClient.GENRE restApiType) {
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
