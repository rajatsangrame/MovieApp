package com.rajatsangrame.movie.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.di.component.DaggerHomeFragmentComponent;
import com.rajatsangrame.movie.di.component.HomeFragmentComponent;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.qualifier.LatestMediaSource;
import com.rajatsangrame.movie.di.qualifier.PopularMediaSource;
import com.rajatsangrame.movie.paging.MovieAdapterNew;
import com.rajatsangrame.movie.paging.MovieDataSource;
import com.rajatsangrame.movie.paging.MovieDataSourceFactory;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    @Inject
    MovieAdapterNew adapter;

    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    ViewModelFactory factory;

    @Inject
    @PopularMediaSource
    MovieDataSourceFactory popularSourceFactory;

    @Inject
    @LatestMediaSource
    MovieDataSourceFactory latestSourceFactory;

    private HomeViewModel homeViewModel;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getDependencies();
        super.onCreate(savedInstanceState);
    }

    private void getDependencies() {
        HomeFragmentComponent component = DaggerHomeFragmentComponent
                .builder()
                .applicationComponent(App.get(getActivity()).getComponent())
                .homeFragmentModule(new HomeFragmentModule(this))
                .build();
        component.injectFragment(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        return view;
    }

}
