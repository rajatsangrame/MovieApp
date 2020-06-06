package com.rajatsangrame.movie.di.component;


import com.rajatsangrame.movie.di.module.DetailActivityModule;
import com.rajatsangrame.movie.di.scope.DetailActivityScope;
import com.rajatsangrame.movie.ui.detail.DetailActivity;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Component(modules = DetailActivityModule.class, dependencies = ApplicationComponent.class)
@DetailActivityScope
public interface DetailActivityComponent {

    void injectDetailActivity(DetailActivity detailActivity);

}
