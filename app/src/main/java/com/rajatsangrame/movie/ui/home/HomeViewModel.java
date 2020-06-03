package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.paging.MovieDataSourceFactory;
import com.rajatsangrame.movie.util.Category;

import javax.inject.Inject;


public class HomeViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository;
    private LiveData<PagedList<Movie>> pagedListPopular;
    private LiveData<PagedList<Movie>> pagedListPopularTv;
    private LiveData<PagedList<Movie>> pagedListNowPlaying;
    private LiveData<PagedList<Movie>> pagedListTopTv;
    private LiveData<PagedList<Movie>> pagedListTopRatedMovie;
    private MovieDataSourceFactory popularMovieSource;
    private MovieDataSourceFactory popularTvSource;
    private MovieDataSourceFactory nowPlayingSource;
    private MovieDataSourceFactory topTvSource;
    private MovieDataSourceFactory topRatedMovieSource;
    private RetrofitApi retrofitApi;

    @Inject
    public HomeViewModel(RestaurantRepository restaurantRepository, RetrofitApi retrofitApi) {
        this.restaurantRepository = restaurantRepository;
        this.retrofitApi = retrofitApi;
        init();
    }

    private void init() {
        popularMovieSource = new MovieDataSourceFactory(Category.POPULAR, retrofitApi);
        popularTvSource = new MovieDataSourceFactory(Category.POPULAR_TV, retrofitApi);
        nowPlayingSource = new MovieDataSourceFactory(Category.NOW_PLAYING, retrofitApi);
        topTvSource = new MovieDataSourceFactory(Category.TOP_TV, retrofitApi);
        topRatedMovieSource = new MovieDataSourceFactory(Category.TOP_RATED_MOVIE, retrofitApi);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        pagedListPopular = new LivePagedListBuilder<>(popularMovieSource, config).build();
        pagedListPopularTv = new LivePagedListBuilder<>(popularTvSource, config).build();
        pagedListNowPlaying = new LivePagedListBuilder<>(nowPlayingSource, config).build();
        pagedListTopTv = new LivePagedListBuilder<>(topTvSource, config).build();
        pagedListTopRatedMovie = new LivePagedListBuilder<>(topRatedMovieSource, config).build();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        popularMovieSource.clear();
        popularTvSource.clear();
        nowPlayingSource.clear();
        topRatedMovieSource.clear();
        topTvSource.clear();
    }

    public LiveData<PagedList<Movie>> getPagedListPopular() {
        return pagedListPopular;
    }

    public LiveData<PagedList<Movie>> getPagedListPopularTv() {
        return pagedListPopularTv;
    }

    public LiveData<PagedList<Movie>> getPagedListNowPlaying() {
        return pagedListNowPlaying;
    }

    public LiveData<PagedList<Movie>> getPagedListTopRatedMovie() {
        return pagedListTopRatedMovie;
    }

    public LiveData<PagedList<Movie>> getPagedListTopTv() {
        return pagedListTopTv;
    }
}
