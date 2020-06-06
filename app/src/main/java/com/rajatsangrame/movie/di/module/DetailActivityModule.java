package com.rajatsangrame.movie.di.module;

import com.rajatsangrame.movie.ui.detail.DetailActivity;

import dagger.Module;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module()
public class DetailActivityModule {

    private final DetailActivity detailActivity;

    public DetailActivityModule(DetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }


}
