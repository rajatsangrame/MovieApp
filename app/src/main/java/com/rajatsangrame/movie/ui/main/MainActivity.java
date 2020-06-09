package com.rajatsangrame.movie.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.ViewPagerAdapter;
import com.rajatsangrame.movie.databinding.ActivityMainBinding;
import com.rajatsangrame.movie.di.component.DaggerMainActivityComponent;
import com.rajatsangrame.movie.di.component.MainActivityComponent;
import com.rajatsangrame.movie.di.module.MainActivityModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private int currentFragment;

    @Inject
    List<Fragment> fragmentList;

    @Inject
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getDependency();
        init();
    }

    private void getDependency() {

        MainActivityComponent component = DaggerMainActivityComponent
                .builder()
                .applicationComponent(App.get(this).getComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
        component.injectMainActivity(this);
    }

    private void init() {

        binding.navigation.setOnNavigationItemSelectedListener(navigationListener);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setAdapter(adapter);
        stackList.add(0);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            if (currentFragment == menuItem.getItemId()) {
                return false;
            }
            currentFragment = menuItem.getItemId();
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    binding.viewPager.setCurrentItem(0, false);
                    binding.toolbar.setTitle(R.string.app_name);
                    addToStackList(0);
                    return true;
                case R.id.navigation_search:
                    binding.viewPager.setCurrentItem(1, false);
                    binding.toolbar.setTitle(R.string.title_search);
                    addToStackList(1);
                    return true;
                case R.id.navigation_saved:
                    binding.viewPager.setCurrentItem(2, false);
                    binding.toolbar.setTitle(R.string.title_saved);
                    addToStackList(2);
                    return true;
            }
            return false;
        }
    };

    //region Custom stack for fragment transaction
    List<Integer> stackList = new ArrayList<>();
    int[] tabs = new int[]{R.id.navigation_home, R.id.navigation_search, R.id.navigation_saved};

    private void addToStackList(int index) {
        if (index == 0) {
            stackList = new ArrayList<>();
            return;
        }
        if (stackList.contains(index)) {
            removeIndex(index);
            stackList.add(index);
            return;
        }
        stackList.add(index);
    }

    private void removeIndex(int index) {
        for (int i = 0; i < stackList.size(); i++) {
            if (index == stackList.get(i)) {
                stackList.remove(i);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (stackList.size() <= 1) {
            super.onBackPressed();
        }
        stackList.remove(stackList.size() - 1);
        int lastIndex = stackList.get(stackList.size() - 1);
        binding.navigation.setSelectedItemId(tabs[lastIndex]);
        if (lastIndex == 0) {
            stackList = new ArrayList<>();
        }
    }
    //endregion
}