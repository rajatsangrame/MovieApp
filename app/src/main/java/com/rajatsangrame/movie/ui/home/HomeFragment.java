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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.di.component.DaggerHomeFragmentComponent;
import com.rajatsangrame.movie.di.component.HomeFragmentComponent;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.qualifier.LatestList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.paging.MovieAdapterNew;
import com.rajatsangrame.movie.util.ViewModelFactory;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";


    @Inject
    @PopularList
    MovieAdapterNew adapter;

    @Inject
    @LatestList
    MovieAdapterNew adapter2;


    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    ViewModelFactory factory;

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
                .applicationComponent(App.get(getContext()).getComponent())
                .homeFragmentModule(new HomeFragmentModule(this))
                .build();
        component.injectFragment(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        homeViewModel.pagedListLatest.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> users) {
                adapter.submitList(users);

            }
        });
        homeViewModel.pagedListPopular.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> users) {
                adapter2.submitList(users);

            }
        });

        return view;
    }

}
