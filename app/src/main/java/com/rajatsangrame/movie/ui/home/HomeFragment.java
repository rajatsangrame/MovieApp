package com.rajatsangrame.movie.ui.home;

import android.content.Intent;
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
import com.rajatsangrame.movie.data.db.MovieDB;
import com.rajatsangrame.movie.databinding.FragmentHomeBinding;
import com.rajatsangrame.movie.di.component.DaggerHomeFragmentComponent;
import com.rajatsangrame.movie.di.component.HomeFragmentComponent;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.qualifier.NowPlayingList;
import com.rajatsangrame.movie.di.qualifier.PopularTvList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.di.qualifier.TopTvShowsList;
import com.rajatsangrame.movie.di.qualifier.TopMovieList;
import com.rajatsangrame.movie.paging.MovieAdapter;
import com.rajatsangrame.movie.ui.detail.DetailActivity;
import com.rajatsangrame.movie.util.ViewModelFactory;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements MovieAdapter.OnClickListener {

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
    @TopTvShowsList
    MovieAdapter topTvShowAdapter;

    @Inject
    @TopMovieList
    MovieAdapter topRatedMovieAdapter;

    @Inject
    ViewModelFactory factory;

    private FragmentHomeBinding binding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        //Pop Movies
        binding.rvPopularMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularMovieAdapter.setListener(this);
        binding.rvPopularMovie.setAdapter(popularMovieAdapter);
        homeViewModel.getPagedListPopular().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                popularMovieAdapter.submitList(movies);
            }
        });

        //Popular Tv
        binding.rvPopularTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularTvAdapter.setListener(this);
        binding.rvPopularTv.setAdapter(popularTvAdapter);
        homeViewModel.getPagedListPopularTv().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                popularTvAdapter.submitList(movies);
            }
        });

        //Now Playing
        binding.rvNowPlayingMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingAdapter.setListener(this);
        binding.rvNowPlayingMovie.setAdapter(nowPlayingAdapter);
        homeViewModel.getPagedListNowPlaying().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                nowPlayingAdapter.submitList(movies);
            }
        });

        //Top Tv Shows
        binding.rvTopTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topTvShowAdapter.setListener(this);
        binding.rvTopTv.setAdapter(topTvShowAdapter);
        homeViewModel.getPagedListTopTv().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                topTvShowAdapter.submitList(movies);
            }
        });

        //Top Rated Movies
        binding.rvTopratedMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedMovieAdapter.setListener(this);
        binding.rvTopratedMovie.setAdapter(topRatedMovieAdapter);
        homeViewModel.getPagedListTopRatedMovie().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                topRatedMovieAdapter.submitList(movies);
            }
        });
    }

    @Override
    public void onDestroy() {
        homeViewModel.dispose();
        super.onDestroy();
    }

    @Override
    public void onItemClicked(MovieDB movie, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("title", movie.getTitle());
        startActivity(intent);
    }
}
