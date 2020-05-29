package com.rajatsangrame.movie.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.di.module.RestaurantRepository;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    RestaurantRepository restaurantRepository;

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
