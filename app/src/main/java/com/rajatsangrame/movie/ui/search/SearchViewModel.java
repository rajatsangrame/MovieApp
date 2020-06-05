package com.rajatsangrame.movie.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.di.module.Repository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class SearchViewModel extends ViewModel {

    private Repository restaurantRepository;

    @Inject
    public SearchViewModel(Repository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        init();
    }

    private void init() {
    }

    public void fetchQuery(String query) {
        restaurantRepository.fetchQuery(query);
    }

    public MutableLiveData<List<SearchResult>> getQueryLiveData() {
        return restaurantRepository.getSearchLiveData();
    }

    public void setQueryLiveData(List<SearchResult> resultList) {
        restaurantRepository.getSearchLiveData().setValue(resultList);
    }
}
