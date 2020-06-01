package com.rajatsangrame.movie.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.model.home.Movie;
import com.rajatsangrame.movie.data.model.search.SearchResult;
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

    public void fetchQuery(String query) {
        restaurantRepository.fetchQuery(query);
    }

    public LiveData<List<SearchResult>> getQueryLiveData() {
        return restaurantRepository.getSearchLiveData();
    }
}
