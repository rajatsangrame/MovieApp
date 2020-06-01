package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.ui.main.MainActivity;

import dagger.Module;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /*
    @Provides
    @MainActivityScope
    public RestaurantAdapter getRestaurantAdapter() {
        return new RestaurantAdapter(mainActivity);
    }
     */

}
