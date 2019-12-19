package com.example.rajat.movie.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.rajat.movie.model.Movie;


/**
 * Created by Rajat Sangrame on 19/10/19.
 * http://github.com/rajatsangrame
 */
public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> userPagedList;
    private LiveData<MovieDataSource> liveDataSource;

    public MovieViewModel() {
        init();
    }

    private void init() {
        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        userPagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();

    }

}
