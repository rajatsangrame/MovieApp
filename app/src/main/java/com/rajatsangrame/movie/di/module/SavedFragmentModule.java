package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.adapter.SavedAdapter;
import com.rajatsangrame.movie.adapter.SearchAdapter;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.di.qualifier.SavedMovieAdapter;
import com.rajatsangrame.movie.di.qualifier.SavedTVAdapter;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.ui.saved.SavedFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class SavedFragmentModule {

    private final SavedFragment fragment;

    public SavedFragmentModule(SavedFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MainActivityScope
    @SavedMovieAdapter
    public SavedAdapter<MovieDB> getSavedMovieAdapter() {
        return new SavedAdapter<MovieDB>();
    }

    @Provides
    @MainActivityScope
    @SavedTVAdapter
    public SavedAdapter<TVDB> getSavedTVAdapter() {
        return new SavedAdapter<TVDB>();
    }

}
