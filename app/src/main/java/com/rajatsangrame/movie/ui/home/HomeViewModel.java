package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.movie.MovieDao;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.db.tv.TvDao;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.paging.BoundaryCallBack;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.data.rest.Category;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomeViewModel extends ViewModel {

    private Repository repository;
    private LiveData<PagedList<MovieDB>> pagedListPopular;
    private LiveData<PagedList<MovieDB>> pagedListNowPlaying;
    private LiveData<PagedList<MovieDB>> pagedListTopRatedMovie;
    private LiveData<PagedList<TVDB>> pagedListPopularTv;
    private LiveData<PagedList<TVDB>> pagedListTopTv;
    private final RetrofitApi retrofitApi;
    private final MovieDao movieDao;
    private final TvDao tvDao;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public HomeViewModel(Repository repository, RetrofitApi retrofitApi) {
        this.repository = repository;
        this.retrofitApi = retrofitApi;
        this.movieDao = repository.getDatabase().movieDao();
        this.tvDao = repository.getDatabase().tvDao();

        initPagedList();
    }

    private void initPagedList() {
        initPopularMovie();
        initPopularTV();
        initNowPlaying();
        initTopTv();
        initTopMovie();
    }

    void initPopularMovie() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack<MovieDB> boundaryCallBack = new BoundaryCallBack(retrofitApi, repository,
                compositeDisposable, Category.POPULAR);
        DataSource.Factory<Integer, MovieDB> factory = movieDao.getPopularDataSource(Category.POPULAR.name());
        pagedListPopular = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();
    }

    void initPopularTV() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack<TVDB> boundaryCallBack = new BoundaryCallBack<>(retrofitApi, repository,
                compositeDisposable, Category.POPULAR_TV);
        DataSource.Factory<Integer, TVDB> factory = tvDao.getPopularTVDataSource(Category.POPULAR_TV.name());
        pagedListPopularTv = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();
    }

    void initNowPlaying() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack<MovieDB> boundaryCallBack = new BoundaryCallBack<>(retrofitApi, repository,
                compositeDisposable, Category.NOW_PLAYING);
        DataSource.Factory<Integer, MovieDB> factory = movieDao.getDataSource(Category.NOW_PLAYING.name());
        pagedListNowPlaying = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();
    }

    void initTopTv() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack<TVDB> boundaryCallBack = new BoundaryCallBack<>(retrofitApi, repository,
                compositeDisposable, Category.TOP_TV);
        DataSource.Factory<Integer, TVDB> factory = tvDao.getTopRatedTVDataSource(Category.TOP_TV.name());
        pagedListTopTv = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();
    }

    void initTopMovie() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        BoundaryCallBack<MovieDB> boundaryCallBack = new BoundaryCallBack<>(retrofitApi, repository,
                compositeDisposable, Category.TOP_RATED_MOVIE);
        DataSource.Factory<Integer, MovieDB> factory = movieDao.getTopRatedDataSource(Category.TOP_RATED_MOVIE.name());
        pagedListTopRatedMovie = new LivePagedListBuilder<>(factory, config)
                .setBoundaryCallback(boundaryCallBack)
                .build();
    }

    public LiveData<PagedList<MovieDB>> getPagedListPopular() {
        return pagedListPopular;
    }

    public LiveData<PagedList<TVDB>> getPagedListPopularTv() {
        return pagedListPopularTv;
    }

    public LiveData<PagedList<MovieDB>> getPagedListNowPlaying() {
        return pagedListNowPlaying;
    }

    public LiveData<PagedList<MovieDB>> getPagedListTopRatedMovie() {
        return pagedListTopRatedMovie;
    }

    public LiveData<PagedList<TVDB>> getPagedListTopTv() {
        return pagedListTopTv;
    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}
