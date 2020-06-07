package com.rajatsangrame.movie.di.component;

import com.rajatsangrame.movie.di.module.SavedFragmentModule;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.ui.saved.SavedFragment;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = SavedFragmentModule.class, dependencies = ApplicationComponent.class)
@MainActivityScope
public interface SavedFragmentComponent {

    void injectFragment(SavedFragment fragment);

}
