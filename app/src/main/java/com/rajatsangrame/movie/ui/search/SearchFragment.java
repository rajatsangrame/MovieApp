package com.rajatsangrame.movie.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rajatsangrame.movie.App;
import com.rajatsangrame.movie.R;
import com.rajatsangrame.movie.adapter.SearchAdapter;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.FragmentSearchBinding;
import com.rajatsangrame.movie.di.component.DaggerSearchFragmentComponent;
import com.rajatsangrame.movie.di.component.SearchFragmentComponent;
import com.rajatsangrame.movie.di.module.Repository;
import com.rajatsangrame.movie.di.module.SearchFragmentModule;
import com.rajatsangrame.movie.util.Utils;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    Repository restaurantRepository;

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

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = binding.etSearch.getText().toString();
                    if (!query.isEmpty()) {
                        Log.i(TAG, "onEditorAction: false");
                        searchViewModel.fetchQuery(query);
                        Utils.hideKeyboard(getContext());
                    }
                }
                Log.i(TAG, "onEditorAction: true");
                return true;
            }
        });

        binding.rvSearch.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvSearch.setAdapter(searchAdapter);
        searchViewModel.getQueryLiveData().observe(getViewLifecycleOwner(), new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(List<SearchResult> searchResults) {
                clearRecyclerView();
                searchAdapter.setMovieList(searchResults);
                if (!searchResults.isEmpty()){
                    clearFocus();
                }
            }
        });
        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFocus();
                searchViewModel.setQueryLiveData(new ArrayList<>());
            }
        });
    }

    private void clearRecyclerView() {
        binding.rvSearch.removeAllViews();
    }

    private void requestFocus() {
        binding.etSearch.setText("");
        binding.etSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.etSearch,InputMethodManager.SHOW_IMPLICIT);

    }

    private void clearFocus() {
        binding.etSearch.clearFocus();
    }
}
