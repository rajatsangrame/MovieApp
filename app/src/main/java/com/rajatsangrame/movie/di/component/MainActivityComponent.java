package com.rajatsangrame.movie.di.component;


import com.rajatsangrame.movie.di.module.MainActivityModule;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = MainActivityModule.class, dependencies = ApplicationComponent.class)
@MainActivityScope
public interface MainActivityComponent {

    void injectMainActivity(MainActivity mainActivity);

}
