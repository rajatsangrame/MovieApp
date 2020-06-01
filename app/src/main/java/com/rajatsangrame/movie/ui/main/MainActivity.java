package com.rajatsangrame.movie.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.ViewPagerAdapter;
import com.rajatsangrame.movie.databinding.ActivityMainBinding;
import com.rajatsangrame.movie.di.component.DaggerMainActivityComponent;
import com.rajatsangrame.movie.di.component.MainActivityComponent;
import com.rajatsangrame.movie.di.module.MainActivityModule;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

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
                    return true;
                case R.id.navigation_search:
                    binding.viewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_saved:
                    binding.viewPager.setCurrentItem(2, false);
                    return true;
            }
            return false;
        }
    };
}

