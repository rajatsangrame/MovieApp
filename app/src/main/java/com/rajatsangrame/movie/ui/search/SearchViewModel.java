package com.rajatsangrame.movie.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.rest.ApiCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class SearchViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public SearchViewModel(Repository repository) {
        this.repository = repository;
    }

    public void fetchQuery(String query, CompositeDisposable disposable, ApiCallback callback) {
        repository.fetchQuery(query, disposable, callback);
    }

    public MutableLiveData<List<SearchResult>> getQueryLiveData() {
        return repository.getSearchLiveData();
    }

    public void setQueryLiveData(List<SearchResult> resultList) {
        repository.getSearchLiveData().setValue(resultList);
    }
}
