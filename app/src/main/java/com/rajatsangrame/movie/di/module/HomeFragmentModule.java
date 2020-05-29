package com.rajatsangrame.movie.di.module;

import androidx.fragment.app.Fragment;

import com.rajatsangrame.movie.paging.MovieAdapterNew;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class HomeFragmentModule {

    private final Fragment fragment;

    public HomeFragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public MovieAdapterNew getRestaurantAdapter() {
        return new MovieAdapterNew(fragment);
    }
}
