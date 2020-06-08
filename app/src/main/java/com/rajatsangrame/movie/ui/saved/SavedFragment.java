package com.rajatsangrame.movie.ui.saved;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.OnClickListener;
import com.rajatsangrame.movie.adapter.SavedAdapter;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.FragmentSavedBinding;
import com.rajatsangrame.movie.di.component.DaggerSavedFragmentComponent;
import com.rajatsangrame.movie.di.component.SavedFragmentComponent;
import com.rajatsangrame.movie.di.module.SavedFragmentModule;
import com.rajatsangrame.movie.di.qualifier.SavedMovieAdapter;
import com.rajatsangrame.movie.di.qualifier.SavedTVAdapter;
import com.rajatsangrame.movie.ui.detail.DetailActivity;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SavedFragment extends Fragment implements OnClickListener {

    public static final String TAG = "SearchFragment";

    @Inject
    @SavedMovieAdapter
    SavedAdapter<MovieDB> movieAdapter;

    @Inject
    @SavedTVAdapter
    SavedAdapter<TVDB> tvAdapter;

    @Inject
    Repository repository;

    @Inject
    ViewModelFactory factory;

    private FragmentSavedBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static SavedFragment newInstance() {
        Bundle args = new Bundle();
        SavedFragment fragment = new SavedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getDependencies();
        super.onCreate(savedInstanceState);
    }

    private void getDependencies() {
        SavedFragmentComponent component = DaggerSavedFragmentComponent
                .builder()
                .applicationComponent(App.get(getContext()).getComponent())
                .savedFragmentModule(new SavedFragmentModule(this))
                .build();
        component.injectFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_saved, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabs();
        SavedViewModel savedViewModel = new ViewModelProvider(this, factory).get(SavedViewModel.class);

        binding.rvSavedMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        movieAdapter.setListener(this);
        binding.rvSavedMovie.setAdapter(movieAdapter);

        binding.rvSavedTv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        tvAdapter.setListener(this);
        binding.rvSavedTv.setAdapter(tvAdapter);

        savedViewModel.getSavedMovie().observe(this, new Observer<List<MovieDB>>() {
            @Override
            public void onChanged(List<MovieDB> movieDBList) {
                movieAdapter.setSaveItemList(movieDBList);
            }
        });
        savedViewModel.getSavedTV().observe(this, new Observer<List<TVDB>>() {
            @Override
            public void onChanged(List<TVDB> tvdbList) {
                tvAdapter.setSaveItemList(tvdbList);
            }
        });
    }

    private void setupTabs() {
        final String[] tabs = new String[]{"Movie", "TV"};
        for (String tab : tabs) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tab));
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        binding.rvSavedMovie.setVisibility(View.VISIBLE);
                        binding.rvSavedTv.setVisibility(View.GONE);
                        break;
                    case 1:
                        binding.rvSavedMovie.setVisibility(View.GONE);
                        binding.rvSavedTv.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onItemClicked(MovieDB movie, View view) {
        if (view.getTag() != null && view.getTag().equals("save")) {
            repository.saveMovie(movie.getId(),null);
            return;
        }
        startDetailActivity(movie.getId(), movie.getTitle(), "movie");
    }

    @Override
    public void onItemClicked(TVDB tv, View view) {
        if (view.getTag() != null && view.getTag().equals("save")) {
            repository.saveTV(tv.getId(),null);
            return;
        }
        startDetailActivity(tv.getId(), tv.getName(), "tv");
    }

    @Override
    public void onItemClicked(SearchResult result, View view) {

    }

    private void startDetailActivity(int id, String name, String type) {

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", name);
        intent.putExtra("type", type);
        startActivity(intent);

    }
}
