package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.di.qualifier.NowPlayingList;
import com.rajatsangrame.movie.di.qualifier.PopularTvList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.di.qualifier.TopTvShowsList;
import com.rajatsangrame.movie.di.qualifier.UpcomingList;
import com.rajatsangrame.movie.paging.MovieAdapter;
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
        return new MovieAdapter(fragment,false);
    }

    @Provides
    @PopularTvList
    public MovieAdapter getPopularTvAdapter() {
        return new MovieAdapter(fragment,false);
    }

    @Provides
    @NowPlayingList
    public MovieAdapter getNowPlaying() {
        return new MovieAdapter(fragment,true);
    }

    @Provides
    @UpcomingList
    public MovieAdapter getUpcomingMovie() {
        return new MovieAdapter(fragment,false);
    }

    @Provides
    @TopTvShowsList
    public MovieAdapter getTopTvShows() {
        return new MovieAdapter(fragment,false);
    }

}
