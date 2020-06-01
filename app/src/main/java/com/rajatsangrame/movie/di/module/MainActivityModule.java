package com.rajatsangrame.movie.di.module;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rajatsangrame.movie.adapter.ViewPagerAdapter;
import com.rajatsangrame.movie.custom.LockableViewPager;
import com.rajatsangrame.movie.di.scope.MainActivityScope;
import com.rajatsangrame.movie.ui.home.HomeFragment;
import com.rajatsangrame.movie.ui.main.MainActivity;
import com.rajatsangrame.movie.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

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

    @Provides
    @MainActivityScope
    public ViewPagerAdapter getViewPagerAdapter(List<Fragment> fragmentList) {
        return new ViewPagerAdapter(mainActivity.getSupportFragmentManager(), 1, fragmentList);
    }

    @Provides
    @MainActivityScope
    public List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(0, HomeFragment.newInstance());
        fragmentList.add(1, SearchFragment.newInstance());
        fragmentList.add(2, new Fragment());
        return fragmentList;
    }

}
