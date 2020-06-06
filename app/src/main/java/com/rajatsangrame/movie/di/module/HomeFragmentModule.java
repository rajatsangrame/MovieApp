package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.di.qualifier.NowPlayingList;
import com.rajatsangrame.movie.di.qualifier.PopularTvList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.di.qualifier.TopTvShowsList;
import com.rajatsangrame.movie.di.qualifier.TopMovieList;
import com.rajatsangrame.movie.adapter.MovieAdapter;
import com.rajatsangrame.movie.adapter.TvAdapter;
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
    public MovieAdapter getPopularAdapter() {
        return new MovieAdapter(fragment);
    }

    @Provides
    @PopularTvList
    public TvAdapter getPopularTvAdapter() {
        return new TvAdapter(fragment);
    }

    @Provides
    @NowPlayingList
    public MovieAdapter getNowPlaying() {
        return new MovieAdapter(fragment);
    }

    @Provides
    @TopMovieList
    public MovieAdapter getTopMovie() {
        return new MovieAdapter(fragment);
    }

    @Provides
    @TopTvShowsList
    public TvAdapter getTopTvShows() {
        return new TvAdapter(fragment);
    }

}
