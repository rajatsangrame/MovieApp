package com.rajatsangrame.movie.ui.search;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
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
import com.rajatsangrame.movie.adapter.OnClickListener;
import com.rajatsangrame.movie.adapter.SearchAdapter;
import com.rajatsangrame.movie.data.db.movie.MovieDB;
import com.rajatsangrame.movie.data.db.tv.TVDB;
import com.rajatsangrame.movie.data.model.search.SearchResult;
import com.rajatsangrame.movie.databinding.FragmentSearchBinding;
import com.rajatsangrame.movie.di.component.DaggerSearchFragmentComponent;
import com.rajatsangrame.movie.di.component.SearchFragmentComponent;
import com.rajatsangrame.movie.data.Repository;
import com.rajatsangrame.movie.di.module.SearchFragmentModule;
import com.rajatsangrame.movie.ui.detail.DetailActivity;
import com.rajatsangrame.movie.util.Utils;
import com.rajatsangrame.movie.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SearchFragment extends Fragment implements OnClickListener {

    public static final String TAG = "SearchFragment";

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    Repository repository;

    @Inject
    ViewModelFactory factory;

    private FragmentSearchBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
                        searchViewModel.fetchQuery(query, compositeDisposable, null);
                        Utils.hideKeyboard(getContext());
                    }
                }
                Log.i(TAG, "onEditorAction: true");
                return true;
            }
        });

        binding.rvSearch.setLayoutManager(new LinearLayoutManager(view.getContext()));
        searchAdapter.setListener(this);
        binding.rvSearch.setAdapter(searchAdapter);
        searchViewModel.getQueryLiveData().observe(getViewLifecycleOwner(), new Observer<List<SearchResult>>() {
            @Override
            public void onChanged(List<SearchResult> searchResults) {
                clearRecyclerView();
                searchAdapter.setMovieList(searchResults);
                if (!searchResults.isEmpty()) {
                    clearFocus();
                }
            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
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
        imm.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT);

    }

    private void clearFocus() {
        binding.etSearch.clearFocus();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onItemClicked(MovieDB movie, View view) {

    }

    @Override
    public void onItemClicked(TVDB movie, View view) {

    }

    @Override
    public void onItemClicked(SearchResult result, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(getString(R.string.id), result.getId());
        intent.putExtra(getString(R.string.title), result.getTitle() != null ? result.getTitle() : result.getName());
        intent.putExtra(getString(R.string.type), result.getMediaType());
        View v1;
        if (result.getMediaType().equals("movie")) {
            v1 = view.findViewById(R.id.tv_movie_title);
        } else {
            v1 = view.findViewById(R.id.tv_title_tv);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(), v1, v1.getTransitionName());
            startActivity(intent, options.toBundle());
            return;
        }
        startActivity(intent);
    }
}
