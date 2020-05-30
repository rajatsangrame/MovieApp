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

import java.util.List;

import javax.inject.Inject;


public class HomeViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository;
    public LiveData<PagedList<Movie>> pagedListPopular;
    public LiveData<PagedList<Movie>> pagedListLatest;
    private RetrofitApi retrofitApi;

    @Inject
    public HomeViewModel(RestaurantRepository restaurantRepository, RetrofitApi retrofitApi) {
        this.restaurantRepository = restaurantRepository;
        this.retrofitApi = retrofitApi;
        init();
    }

    public LiveData<List<Object>> getAllRestaurants() {
        return restaurantRepository.getRestaurantList();
    }

    private void init() {

        MovieDataSourceFactory popularMovieSource = new MovieDataSourceFactory(Category.POPULAR, retrofitApi);
        MovieDataSourceFactory latestSourceFactory = new MovieDataSourceFactory(Category.TOP_RATED, retrofitApi);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        pagedListPopular = new LivePagedListBuilder<>(popularMovieSource, config).build();
        pagedListLatest = new LivePagedListBuilder<>(latestSourceFactory, config).build();

    }
}
