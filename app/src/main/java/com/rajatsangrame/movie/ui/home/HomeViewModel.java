package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.qualifier.LatestMediaSource;
import com.rajatsangrame.movie.di.qualifier.PopularMediaSource;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.paging.MovieDataSourceFactory;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    RestaurantRepository restaurantRepository;
    MovieDataSourceFactory popularMovieSource;
    MovieDataSourceFactory latestSourceFactory;

//    @Inject
//    public HomeViewModel(RestaurantRepository restaurantRepository,
//                         MovieDataSourceFactory popularSourceFactory,
//                         MovieDataSourceFactory latestSourceFactory) {
//        this.restaurantRepository = restaurantRepository;
//        this.popularMovieSource = popularSourceFactory;
//        this.latestSourceFactory = latestSourceFactory;
//    }

    @Inject
    public HomeViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void getRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public LiveData<List<Object>> getAllRestaurants() {
        return restaurantRepository.getRestaurantList();
    }

}
