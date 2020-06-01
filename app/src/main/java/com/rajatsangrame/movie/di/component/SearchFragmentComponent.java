package com.rajatsangrame.movie.di.component;

import com.rajatsangrame.movie.di.module.SearchFragmentModule;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.ui.search.SearchFragment;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = SearchFragmentModule.class, dependencies = ApplicationComponent.class)
@MainActivityScope
public interface SearchFragmentComponent {

    void injectFragment(SearchFragment fragment);

}
