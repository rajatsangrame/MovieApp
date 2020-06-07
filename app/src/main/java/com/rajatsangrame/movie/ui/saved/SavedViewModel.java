package com.rajatsangrame.movie.ui.saved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.rest.ApiCallback;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Rajat Sangrame on 1/6/20.
 * http://github.com/rajatsangrame
 */
public class SavedViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public SavedViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<MovieDB>> getSavedMovie() {
        return repository.getLiveDataSavedMovie();
    }

    public LiveData<List<TVDB>> getSavedTV() {
        return repository.getLiveDataSavedTV();
    }

}
