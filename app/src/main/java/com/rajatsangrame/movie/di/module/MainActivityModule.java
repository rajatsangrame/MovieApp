package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.activities.MainActivity;
import com.rajatsangrame.movie.di.scope.MainActivityScope;

import dagger.Module;
import dagger.Provides;

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
