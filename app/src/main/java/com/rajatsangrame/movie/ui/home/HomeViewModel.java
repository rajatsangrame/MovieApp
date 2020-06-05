package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.db.MovieDao;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.paging.BoundaryCallBack;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.data.rest.Category;

import javax.inject.Inject;


public class HomeViewModel extends ViewModel {

    private Repository repository;
    private LiveData<PagedList<MovieDB>> pagedListPopular;
    //    private LiveData<PagedList<Movie>> pagedListPopularTv;
//    private LiveData<PagedList<Movie>> pagedListNowPlaying;
//    private LiveData<PagedList<Movie>> pagedListTopTv;
//    private LiveData<PagedList<Movie>> pagedListTopRatedMovie;
    private RetrofitApi retrofitApi;
    private MovieDao movieDao;

    @Inject
    public HomeViewModel(Repository repository, RetrofitApi retrofitApi) {
        this.repository = repository;
        this.retrofitApi = retrofitApi;
        this.movieDao = repository.getDatabase().movieDao();
        init();
    }

    private void init() {

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack boundaryCallBack = new BoundaryCallBack(retrofitApi, movieDao, Category.POPULAR);
        DataSource.Factory<Integer, MovieDB> factory = movieDao.getDataSource(Category.POPULAR.name());
        pagedListPopular = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();

//        pagedListPopularTv = new LivePagedListBuilder<>(popularTvSource, config).build();
//        pagedListNowPlaying = new LivePagedListBuilder<>(nowPlayingSource, config).build();
//        pagedListTopTv = new LivePagedListBuilder<>(topTvSource, config).build();
//        pagedListTopRatedMovie = new LivePagedListBuilder<>(topRatedMovieSource, config).build();
    }

    public LiveData<PagedList<MovieDB>> getPagedListPopular() {
        return pagedListPopular;
    }
//
//    public LiveData<PagedList<Movie>> getPagedListPopularTv() {
//        return pagedListPopularTv;
//    }
//
//    public LiveData<PagedList<Movie>> getPagedListNowPlaying() {
//        return pagedListNowPlaying;
//    }
//
//    public LiveData<PagedList<Movie>> getPagedListTopRatedMovie() {
//        return pagedListTopRatedMovie;
//    }
//
//    public LiveData<PagedList<Movie>> getPagedListTopTv() {
//        return pagedListTopTv;
//    }
}
