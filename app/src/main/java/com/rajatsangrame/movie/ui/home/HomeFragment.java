package com.rajatsangrame.movie.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.databinding.FragmentHomeBinding;
import com.rajatsangrame.movie.di.component.DaggerHomeFragmentComponent;
import com.rajatsangrame.movie.di.component.HomeFragmentComponent;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.qualifier.NowPlayingList;
import com.rajatsangrame.movie.di.qualifier.PopularTvList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.di.qualifier.TopTvShowsList;
import com.rajatsangrame.movie.di.qualifier.UpcomingList;
import com.rajatsangrame.movie.model.Movie;
import com.rajatsangrame.movie.paging.MovieAdapter;
import com.rajatsangrame.movie.util.ViewModelFactory;

import javax.inject.Inject;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    @Inject
    @PopularList
    MovieAdapter popularMovieAdapter;

    @Inject
    @PopularTvList
    MovieAdapter popularTvAdapter;

    @Inject
    @NowPlayingList
    MovieAdapter nowPlayingAdapter;

    @Inject
    @UpcomingList
    MovieAdapter upcomingAdapter;

    @Inject
    @TopTvShowsList
    MovieAdapter topTvShowAdapter;

    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    ViewModelFactory factory;

    private FragmentHomeBinding binding;

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
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModel homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        //Pop Movies
        binding.rvPopularMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopularMovie.setAdapter(popularMovieAdapter);
        homeViewModel.pagedListPopular.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                popularMovieAdapter.submitList(movies);
            }
        });

        //Popular Tv
        binding.rvPopularTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvPopularTv.setAdapter(popularTvAdapter);
        homeViewModel.pagedListPopularTv.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                popularTvAdapter.submitList(movies);
            }
        });

        //Now Playing
        binding.rvNowPlayingMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvNowPlayingMovie.setAdapter(nowPlayingAdapter);
        homeViewModel.pagedListNowPlaying.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                nowPlayingAdapter.submitList(movies);
            }
        });

        //Upcoming
        binding.rvUpcomingMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvUpcomingMovie.setAdapter(upcomingAdapter);
        homeViewModel.pagedListUpcoming.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                upcomingAdapter.submitList(movies);
            }
        });

        //Top Tv Shows
        binding.rvTopTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvTopTv.setAdapter(topTvShowAdapter);
        homeViewModel.pagedListTopTv.observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                topTvShowAdapter.submitList(movies);
            }
        });


    }
}
