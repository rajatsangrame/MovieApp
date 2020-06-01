package com.rajatsangrame.movie.di.component;

import com.bumptech.glide.Glide;
import com.rajatsangrame.movie.di.module.ApplicationModule;
import com.rajatsangrame.movie.di.module.GlideModule;
import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.scope.ApplicationScope;
import com.rajatsangrame.movie.data.rest.RetrofitApi;
import com.rajatsangrame.movie.util.ViewModelFactory;

import dagger.Component;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class, GlideModule.class})
public interface ApplicationComponent {

    RetrofitApi getApiService();

    Glide getGlide(); // For custom glide

    RestaurantRepository getRepository();

    ViewModelFactory getViewModelFactory();

}
