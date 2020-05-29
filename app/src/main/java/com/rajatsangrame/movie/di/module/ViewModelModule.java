package com.rajatsangrame.movie.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.rajatsangrame.movie.di.key.ViewModelKey;
import com.rajatsangrame.movie.ui.home.HomeViewModel;
import com.rajatsangrame.movie.util.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Rajat Sangrame on 24/5/20.
 * http://github.com/rajatsangrame
 */
@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel providePlayerViewModel(HomeViewModel restaurantViewModel);
}
