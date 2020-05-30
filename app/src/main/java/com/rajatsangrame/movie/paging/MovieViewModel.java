package com.rajatsangrame.movie.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.util.Category;


/**
 * Created by Rajat Sangrame on 19/10/19.
 * http://github.com/rajatsangrame
 */
public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> userPagedList;
    public LiveData<PagedList<Movie>> userPagedList2;

    public MovieViewModel() {
        init();
    }

    private void init() {

//        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory(Category.POPULAR);
//        MovieDataSourceFactory itemDataSourceFactory2 = new MovieDataSourceFactory(Category.TOP_RATED);
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setPageSize(MovieDataSource.PAGE_SIZE)
//                .build();
//        userPagedList = new LivePagedListBuilder<>(itemDataSourceFactory, config).build();
//        userPagedList2 = new LivePagedListBuilder<>(itemDataSourceFactory2, config).build();

    }
}
