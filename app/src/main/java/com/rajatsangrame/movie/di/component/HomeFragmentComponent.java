package com.rajatsangrame.movie.di.component;


import androidx.fragment.app.Fragment;

import com.rajatsangrame.movie.activities.MainActivity;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.module.MainActivityModule;
import com.rajatsangrame.movie.di.scope.MainActivityScope;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = HomeFragmentModule.class, dependencies = ApplicationComponent.class)
@MainActivityScope
public interface HomeFragmentComponent {

    void injectFragment(Fragment fragment);

}
