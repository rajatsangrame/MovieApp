package com.rajatsangrame.movie.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.network.RetrofitApi;
import com.rajatsangrame.movie.util.Category;

public class MovieDataSourceFactory extends DataSource.Factory<Long, Movie> {

    private Category category;
    private RetrofitApi retrofitApi;

    public MovieDataSourceFactory(Category category, RetrofitApi retrofitApi) {
        this.category = category;
        this.retrofitApi = retrofitApi;
    }

    public MutableLiveData<MovieDataSource> movieLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Long, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(category, retrofitApi);
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }

    private MovieDataSourceFactory() {
    }
}
