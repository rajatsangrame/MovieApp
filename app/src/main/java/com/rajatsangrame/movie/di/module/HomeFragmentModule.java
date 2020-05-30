package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.di.qualifier.LatestList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.paging.MovieAdapterNew;
import com.rajatsangrame.movie.ui.home.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class HomeFragmentModule {

    private final HomeFragment fragment;

    public HomeFragmentModule(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PopularList
    public MovieAdapterNew getPopularAdapter() {
        return new MovieAdapterNew(fragment);
    }

    @Provides
    @LatestList
    public MovieAdapterNew getLatestAdapter() {
        return new MovieAdapterNew(fragment);
    }

}
