package com.rajatsangrame.movie.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.Repository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class SearchViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public SearchViewModel(Repository repository) {
        this.repository = repository;
        init();
    }

    private void init() {
    }

    public void fetchQuery(String query) {
        repository.fetchQuery(query);
    }

    public MutableLiveData<List<SearchResult>> getQueryLiveData() {
        return repository.getSearchLiveData();
    }

    public void setQueryLiveData(List<SearchResult> resultList) {
        repository.getSearchLiveData().setValue(resultList);
    }
}
