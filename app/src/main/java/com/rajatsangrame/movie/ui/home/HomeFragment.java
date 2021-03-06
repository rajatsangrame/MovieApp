package com.rajatsangrame.movie.ui.home;

import android.app.ActivityOptions;
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
import com.rajatsangrame.movie.adapter.OnClickListener;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.FragmentHomeBinding;
import com.rajatsangrame.movie.di.component.DaggerHomeFragmentComponent;
import com.rajatsangrame.movie.di.component.HomeFragmentComponent;
import com.rajatsangrame.movie.di.module.HomeFragmentModule;
import com.rajatsangrame.movie.di.qualifier.NowPlayingList;
import com.rajatsangrame.movie.di.qualifier.PopularTvList;
import com.rajatsangrame.movie.di.qualifier.PopularList;
import com.rajatsangrame.movie.di.qualifier.TopTvShowsList;
import com.rajatsangrame.movie.di.qualifier.TopMovieList;
import com.rajatsangrame.movie.adapter.MovieAdapter;
import com.rajatsangrame.movie.adapter.TvAdapter;
import com.rajatsangrame.movie.ui.detail.DetailActivity;
import com.rajatsangrame.movie.util.ViewModelFactory;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements OnClickListener {

    public static final String TAG = "HomeFragment";

    @Inject
    @PopularList
    MovieAdapter popularMovieAdapter;

    @Inject
    @PopularTvList
    TvAdapter popularTvAdapter;

    @Inject
    @NowPlayingList
    MovieAdapter nowPlayingAdapter;

    @Inject
    @TopTvShowsList
    TvAdapter topTvShowAdapter;

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
                binding.layoutPopularMovies.setVisibility(View.VISIBLE);
                popularMovieAdapter.submitList(movies);
            }
        });

        //Popular Tv
        binding.rvPopularTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularTvAdapter.setListener(this);
        binding.rvPopularTv.setAdapter(popularTvAdapter);
        homeViewModel.getPagedListPopularTv().observe(getViewLifecycleOwner(), new Observer<PagedList<TVDB>>() {
            @Override
            public void onChanged(PagedList<TVDB> movies) {
                binding.layoutPopTvShows.setVisibility(View.VISIBLE);
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
                binding.layoutNowPlayingMovie.setVisibility(View.VISIBLE);
                nowPlayingAdapter.submitList(movies);
            }
        });

        //Top Tv Shows
        binding.rvTopTv.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topTvShowAdapter.setListener(this);
        binding.rvTopTv.setAdapter(topTvShowAdapter);
        homeViewModel.getPagedListTopTv().observe(getViewLifecycleOwner(), new Observer<PagedList<TVDB>>() {
            @Override
            public void onChanged(PagedList<TVDB> movies) {
                binding.layoutTopTvShows.setVisibility(View.VISIBLE);
                topTvShowAdapter.submitList(movies);
            }
        });

        //Top Rated Movies
        binding.rvTopRatedMovie.setLayoutManager(new LinearLayoutManager(
                view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        topRatedMovieAdapter.setListener(this);
        binding.rvTopRatedMovie.setAdapter(topRatedMovieAdapter);
        homeViewModel.getPagedListTopRatedMovie().observe(getViewLifecycleOwner(), new Observer<PagedList<MovieDB>>() {
            @Override
            public void onChanged(PagedList<MovieDB> movies) {
                binding.layoutTopRatedMovie.setVisibility(View.VISIBLE);
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
        intent.putExtra(getString(R.string.id), movie.getId());
        intent.putExtra(getString(R.string.title), movie.getTitle());
        intent.putExtra(getString(R.string.type), "movie");
        View v1 = view.findViewById(R.id.iv_poster);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(), v1, v1.getTransitionName());
            startActivity(intent, options.toBundle());
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onItemClicked(TVDB tv, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(getString(R.string.id), tv.getId());
        intent.putExtra(getString(R.string.title), tv.getName());
        intent.putExtra(getString(R.string.type), "tv");
        View v1 = view.findViewById(R.id.iv_poster);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(), v1, v1.getTransitionName());
            startActivity(intent, options.toBundle());
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onItemClicked(SearchResult movie, View view) {

    }

}
