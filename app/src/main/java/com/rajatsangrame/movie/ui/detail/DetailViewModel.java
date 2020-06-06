package com.rajatsangrame.movie.ui.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.rest.ApiCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class DetailViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public DetailViewModel(Repository repository) {
        this.repository = repository;
        init();
    }

    private void init() {
    }

    public void fetchMovieDetail(int id, CompositeDisposable disposable, ApiCallback callback) {
        repository.fetchMovieDetail(id, disposable, callback);
    }

    public MutableLiveData<MovieDB> getMovieDetail() {
        return repository.getLiveDataMovieDetail();
    }

    public void setQueryLiveData(List<SearchResult> resultList) {
        repository.getSearchLiveData().setValue(resultList);
    }
}
