package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.data.model.Movie;
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
    private LiveData<PagedList<Movie>> pagedListUpcoming;
    private LiveData<PagedList<Movie>> pagedListTopTv;
    private MovieDataSourceFactory popularMovieSource;
    private MovieDataSourceFactory popularTvSource;
    private MovieDataSourceFactory nowPlayingSource;
    private MovieDataSourceFactory upComingSource;
    private MovieDataSourceFactory topTvSource;
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
        upComingSource = new MovieDataSourceFactory(Category.UPCOMING, retrofitApi);
        topTvSource = new MovieDataSourceFactory(Category.TOP_TV, retrofitApi);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        pagedListPopular = new LivePagedListBuilder<>(popularMovieSource, config).build();
        pagedListPopularTv = new LivePagedListBuilder<>(popularTvSource, config).build();
        pagedListNowPlaying = new LivePagedListBuilder<>(nowPlayingSource, config).build();
        pagedListUpcoming = new LivePagedListBuilder<>(upComingSource, config).build();
        pagedListTopTv = new LivePagedListBuilder<>(topTvSource, config).build();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        popularMovieSource.clear();
        popularTvSource.clear();
        nowPlayingSource.clear();
        upComingSource.clear();
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

    public LiveData<PagedList<Movie>> getPagedListUpcoming() {
        return pagedListUpcoming;
    }

    public LiveData<PagedList<Movie>> getPagedListTopTv() {
        return pagedListTopTv;
    }
}
