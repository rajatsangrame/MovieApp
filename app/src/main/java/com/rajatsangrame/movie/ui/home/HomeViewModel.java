package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.network.RetrofitApi;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.paging.MovieDataSourceFactory;
import com.rajatsangrame.movie.util.Category;

import javax.inject.Inject;


public class HomeViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository;
    public LiveData<PagedList<Movie>> pagedListPopular;
    public LiveData<PagedList<Movie>> pagedListPopularTv;
    public LiveData<PagedList<Movie>> pagedListNowPlaying;
    public LiveData<PagedList<Movie>> pagedListUpcoming;
    public LiveData<PagedList<Movie>> pagedListTopTv;
    private RetrofitApi retrofitApi;

    @Inject
    public HomeViewModel(RestaurantRepository restaurantRepository, RetrofitApi retrofitApi) {
        this.restaurantRepository = restaurantRepository;
        this.retrofitApi = retrofitApi;
        init();
    }

    private void init() {
        MovieDataSourceFactory popularMovieSource = new MovieDataSourceFactory(Category.POPULAR, retrofitApi);
        MovieDataSourceFactory popularTvSource = new MovieDataSourceFactory(Category.POPULAR_TV, retrofitApi);
        MovieDataSourceFactory nowPlayingSource = new MovieDataSourceFactory(Category.NOW_PLAYING, retrofitApi);
        MovieDataSourceFactory upComingSource = new MovieDataSourceFactory(Category.UPCOMING, retrofitApi);
        MovieDataSourceFactory topTvSource = new MovieDataSourceFactory(Category.TOP_TV, retrofitApi);
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
}
