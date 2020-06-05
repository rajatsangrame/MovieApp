package com.rajatsangrame.movie.di.component;

import com.rajatsangrame.movie.di.module.ApplicationModule;
import com.rajatsangrame.movie.di.module.GlideModule;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.di.scope.ApplicationScope;
import com.rajatsangrame.movie.util.ViewModelFactory;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class, GlideModule.class})
public interface ApplicationComponent {

    Repository getRepository();

    ViewModelFactory getViewModelFactory();

}
