package com.rajatsangrame.movie.ui.search;

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
import com.rajatsangrame.movie.data.model.Movie;
import com.rajatsangrame.movie.databinding.FragmentSearchBinding;
import com.rajatsangrame.movie.di.component.DaggerSearchFragmentComponent;
import com.rajatsangrame.movie.di.component.SearchFragmentComponent;
import com.rajatsangrame.movie.di.module.RestaurantRepository;
import com.rajatsangrame.movie.di.module.SearchFragmentModule;
import com.rajatsangrame.movie.paging.MovieAdapter;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    @Inject
    MovieAdapter searchAdapter;

    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    ViewModelFactory factory;

    private FragmentSearchBinding binding;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getDependencies();
        super.onCreate(savedInstanceState);
    }

    private void getDependencies() {
        SearchFragmentComponent component = DaggerSearchFragmentComponent
                .builder()
                .applicationComponent(App.get(getContext()).getComponent())
                .searchFragmentModule(new SearchFragmentModule(this))
                .build();
        component.injectFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchViewModel searchViewModel = new ViewModelProvider(this, factory).get(SearchViewModel.class);

        binding.rvSearch.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvSearch.setAdapter(searchAdapter);
        searchViewModel.getListLiveData("big bang theory").observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

            }
        });
    }
}
