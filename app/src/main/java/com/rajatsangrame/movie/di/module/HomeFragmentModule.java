package com.rajatsangrame.movie.di.module;

import androidx.fragment.app.Fragment;

import com.rajatsangrame.movie.di.qualifier.LatestMediaSource;
import com.rajatsangrame.movie.di.qualifier.PopularMediaSource;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.paging.MovieAdapterNew;
import com.rajatsangrame.movie.paging.MovieDataSourceFactory;
import com.rajatsangrame.movie.ui.home.HomeFragment;
import com.rajatsangrame.movie.util.Genre;

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
    @MainActivityScope
    public MovieAdapterNew getRestaurantAdapter() {
        return new MovieAdapterNew(fragment);
    }

    @Provides
    @MainActivityScope
    @PopularMediaSource
    public MovieDataSourceFactory getPopularDataSourceFactory() {
        return new MovieDataSourceFactory(Genre.POPULAR);
    }

    @Provides
    @MainActivityScope
    @LatestMediaSource
    public MovieDataSourceFactory getLatestDataSourceFactory() {
        return new MovieDataSourceFactory(Genre.LATEST);
    }
}
