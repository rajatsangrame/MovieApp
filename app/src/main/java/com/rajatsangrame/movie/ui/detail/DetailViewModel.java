package com.rajatsangrame.movie.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.rest.ApiCallback;

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

    public void updateMovieSave(int id, Repository.InsertCallback callback) {
        repository.saveMovie(id, callback);
    }

    public void updateTVSave(int id, Repository.InsertCallback callback) {
        repository.saveTV(id, callback);
    }
}
