package com.rajatsangrame.movie.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.Category;

public class MovieDataSourceFactory extends DataSource.Factory<Long, Movie> {

    private Category category;
    private RetrofitApi retrofitApi;
    private MovieDataSource movieDataSource;

    public MovieDataSourceFactory(Category category, RetrofitApi retrofitApi) {
        this.category = category;
        this.retrofitApi = retrofitApi;
    }

    public MutableLiveData<MovieDataSource> movieLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Long, Movie> create() {
        movieDataSource = new MovieDataSource(category, retrofitApi);
        movieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }

    public void clear() {
        movieDataSource.clear();
    }

    private MovieDataSourceFactory() {
    }
}
