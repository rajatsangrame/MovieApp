package com.rajatsangrame.movie.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.data.rest.ApiCallback;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class DetailViewModel extends ViewModel {

    private Repository repository;
    //private MutableLiveData<MovieDB> movieLiveData = new MutableLiveData<>();
    //private MutableLiveData<TVDB> tvLiveData = new MutableLiveData<>();

    @Inject
    public DetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public void fetchMovieDetail(int id, CompositeDisposable disposable, ApiCallback callback) {
        repository.fetchMovieDetail(id, disposable, callback);

    }

    public void fetchTVDetail(int id, CompositeDisposable disposable, ApiCallback callback) {
        repository.fetchTVDetail(id, disposable, callback);
    }

    public LiveData<MovieDB> getMovieDetail(int id) {
        return repository.getLiveDataMovieDetail(id);
    }

    public LiveData<TVDB> getTvDetail(int id) {
        return repository.getLiveDataTVDetail(id);
    }

}
