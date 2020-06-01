package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.paging.MovieAdapter;
import com.rajatsangrame.movie.ui.search.SearchFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class SearchFragmentModule {

    private final SearchFragment fragment;

    public SearchFragmentModule(SearchFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MainActivityScope
    public MovieAdapter getSearchAdapter() {
        return new MovieAdapter(fragment, false);
    }

}
