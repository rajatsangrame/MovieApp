package com.rajatsangrame.movie.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.rajatsangrame.movie.data.model.Movie;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.di.module.RestaurantRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class SearchViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository;

    @Inject
    public SearchViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        init();
    }

    private void init() {
    }

    public LiveData<List<Movie>> getListLiveData(String query) {
        return restaurantRepository.getSearchLiveData(query);
    }
}
